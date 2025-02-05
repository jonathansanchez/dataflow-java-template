package com.verix.forecast.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.cloud.bigquery.*;
import com.verix.forecast.domain.model.Remediation;
import com.verix.forecast.infrastructure.config.JobOptions;
import com.verix.forecast.infrastructure.repository.BigQueryRepository;
import com.verix.forecast.infrastructure.repository.model.RemediationTableSchema;
import com.verix.forecast.infrastructure.streaming.transformation.RemediationToTableRow;
import com.verix.forecast.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.forecast.infrastructure.streaming.transformation.StringToRemediationTransformation;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeamDataPipelineIntegrationTest {

    private static final String PROJECT_ID      = "PROJECT_ID";
    private static final String BUCKET_NAME     = "BUCKET_NAME";
    private static final String TEMP_LOCATION   = "gs://" + BUCKET_NAME + "/temp-files";
    private static final String CSV_FILE_NAME   = "CSV_FILE_NAME.csv";
    private static final String INPUT_FILE      = "gs://" + BUCKET_NAME + "/data/" + CSV_FILE_NAME;
    private static final String DATASET_NAME    = "DATASET_NAME";
    private static final String TABLE_NAME      = "TABLE_NAME";

    private static JobOptions options;
    private static BigQuery bigQuery;
    private static RemoveLineBreaksTransformation removeLineBreaksTransformation;
    private static StringToRemediationTransformation stringToRemediationTransformation;
    private static RemediationToTableRow remediationToTableRow;
    private static RemediationTableSchema remediationTableSchema;
    private static BigQueryRepository bigQueryRepository;

    @BeforeAll
    public static void setUp() throws IOException {
        PipelineOptionsFactory.register(JobOptions.class);
        options                             = PipelineOptionsFactory.as(JobOptions.class);
        bigQuery                            = BigQueryOptions.getDefaultInstance().getService();
        removeLineBreaksTransformation      = new RemoveLineBreaksTransformation();
        stringToRemediationTransformation   = new StringToRemediationTransformation();
        remediationToTableRow               = new RemediationToTableRow();
        remediationTableSchema              = new RemediationTableSchema(new TableSchema());
        bigQueryRepository                  = new BigQueryRepository(options, remediationTableSchema);

        options.setTempLocation(TEMP_LOCATION);
        options.setInput(INPUT_FILE);
        options.setOutput(String.format("%s:%s.%s", PROJECT_ID, DATASET_NAME, TABLE_NAME));

        // Delete table before insert
        bigQuery.delete(TableId.of(PROJECT_ID, DATASET_NAME, TABLE_NAME));

        // Create a new table in BigQuery
        TableId tableId = TableId.of(PROJECT_ID, DATASET_NAME, TABLE_NAME);
        Schema schema = Schema.of(
                Field.of("strategy", StandardSQLTypeName.STRING),
                Field.of("apm_code", StandardSQLTypeName.STRING),
                Field.of("component", StandardSQLTypeName.STRING),
                Field.of("version", StandardSQLTypeName.STRING),
                Field.of("action", StandardSQLTypeName.STRING),
                Field.newBuilder("new_version", StandardSQLTypeName.STRING).setMode(Field.Mode.NULLABLE).build(),
                Field.of("delivery_date", StandardSQLTypeName.DATE)
        );
        TableDefinition tableDefinition = StandardTableDefinition.of(schema);
        TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();
        bigQuery.create(tableInfo);
    }

    @Test
    void run() {
        BeamDataPipeline pipeline = new BeamDataPipeline(options,
                Pipeline.create(options),
                bigQueryRepository,
                removeLineBreaksTransformation,
                stringToRemediationTransformation,
                remediationToTableRow);

        pipeline.run();
        AtomicInteger rowCount = countResult();
        assertEquals(10, rowCount.get());
    }

    @Test
    public void run_pipeline() {
        Pipeline pipeline = Pipeline.create(options);

        PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));
        //PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from("src/main/resources/forecast.csv"));

        PCollection<String> cleanedLines = rawData.apply("Transform: Sanitization line breaks", ParDo.of(removeLineBreaksTransformation));

        PCollection<Remediation> remediationList = cleanedLines.apply("Transform: Format from String to Remediation Class and fields", ParDo.of(stringToRemediationTransformation));

        PCollection<TableRow> tableRows = remediationList.apply("Transform: Format from Remediation Class to Table Row Class", ParDo.of(remediationToTableRow));

        tableRows.apply("Load: Write Remediation into BigQuery", bigQueryRepository.writeToBigQuery());

        pipeline.run().waitUntilFinish();

        // Count data from BigQuery
        AtomicInteger rowCount = countResult();
        assertEquals(10, rowCount.get());
    }

    @NotNull
    private static AtomicInteger countResult() {
        TableResult result     = bigQuery.listTableData(TableId.of(PROJECT_ID, DATASET_NAME, TABLE_NAME), BigQuery.TableDataListOption.pageSize(10));
        AtomicInteger rowCount = new AtomicInteger();
        result.iterateAll().forEach(row -> {
            System.out.println(row.toString());
            rowCount.getAndIncrement();
        });
        return rowCount;
    }

    @AfterAll
    public static void tearDown() {
        // Delete table from BigQuery
        bigQuery.delete(TableId.of(PROJECT_ID, DATASET_NAME, TABLE_NAME));
    }
}