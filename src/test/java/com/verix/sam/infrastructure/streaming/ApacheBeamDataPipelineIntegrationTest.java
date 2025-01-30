package com.verix.sam.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.cloud.bigquery.*;
import com.verix.sam.domain.model.Sam;
import com.verix.sam.infrastructure.config.JobOptions;
import com.verix.sam.infrastructure.repository.BigQueryRepository;
import com.verix.sam.infrastructure.repository.model.SamTableSchema;
import com.verix.sam.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.sam.infrastructure.streaming.transformation.SamToTableRow;
import com.verix.sam.infrastructure.streaming.transformation.StringToSamTransformation;
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

class ApacheBeamDataPipelineIntegrationTest {

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
    private static StringToSamTransformation stringToSamTransformation;
    private static SamToTableRow samToTableRow;
    private static SamTableSchema samTableSchema;
    private static BigQueryRepository bigQueryRepository;

    @BeforeAll
    public static void setUp() throws IOException {
        PipelineOptionsFactory.register(JobOptions.class);
        options                         = PipelineOptionsFactory.as(JobOptions.class);
        bigQuery                        = BigQueryOptions.getDefaultInstance().getService();
        removeLineBreaksTransformation  = new RemoveLineBreaksTransformation();
        stringToSamTransformation       = new StringToSamTransformation();
        samToTableRow                   = new SamToTableRow();
        samTableSchema                  = new SamTableSchema(new TableSchema());
        bigQueryRepository              = new BigQueryRepository(options, samTableSchema);

        options.setTempLocation(TEMP_LOCATION);
        options.setInput(INPUT_FILE);
        options.setOutput(String.format("%s:%s.%s", PROJECT_ID, DATASET_NAME, TABLE_NAME));

        // Delete table before insert
        bigQuery.delete(TableId.of(PROJECT_ID, DATASET_NAME, TABLE_NAME));

        // Create a new table in BigQuery
        TableId tableId = TableId.of(PROJECT_ID, DATASET_NAME, TABLE_NAME);
        Schema schema = Schema.of(
                Field.of("publisher", StandardSQLTypeName.STRING),
                Field.of("category", StandardSQLTypeName.STRING),
                Field.of("product", StandardSQLTypeName.STRING),
                Field.of("product_version", StandardSQLTypeName.STRING),
                Field.of("version", StandardSQLTypeName.STRING),
                Field.of("full_version", StandardSQLTypeName.STRING),
                Field.of("edition", StandardSQLTypeName.STRING),
                Field.of("internal_availability", StandardSQLTypeName.DATE),
                Field.of("internal_end_of_support", StandardSQLTypeName.DATE),
                Field.of("publisher_availability", StandardSQLTypeName.DATE),
                Field.of("publisher_end_of_support", StandardSQLTypeName.DATE),
                Field.of("publisher_end_of_extended_support", StandardSQLTypeName.DATE),
                Field.of("publisher_end_of_life", StandardSQLTypeName.DATE),
                Field.of("source", StandardSQLTypeName.STRING)
        );
        TableDefinition tableDefinition = StandardTableDefinition.of(schema);
        TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();
        bigQuery.create(tableInfo);
    }

    @Test
    void run() {
        ApacheBeamDataPipeline pipeline = new ApacheBeamDataPipeline(options,
                Pipeline.create(options),
                bigQueryRepository,
                removeLineBreaksTransformation,
                stringToSamTransformation,
                samToTableRow);

        pipeline.run();
        AtomicInteger rowCount = countResult();
        assertEquals(10, rowCount.get());
    }

    @Test
    public void run_pipeline() {
        Pipeline pipeline = Pipeline.create(options);

        PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(options.getInput()));

        PCollection<String> cleanedLines = rawData.apply("Transform: Sanitization line breaks", ParDo.of(removeLineBreaksTransformation));

        PCollection<Sam> samList = cleanedLines.apply("Transform: Format from String to SAM Class and fields", ParDo.of(stringToSamTransformation));

        PCollection<TableRow> tableRows = samList.apply("Transform: Format from SAM Class to Table Row Class", ParDo.of(samToTableRow));

        tableRows.apply("Load: Write SAM into BigQuery", bigQueryRepository.writeToBigQuery());

        pipeline.run().waitUntilFinish();

        // Count data from BigQuery
        AtomicInteger rowCount = countResult();
        assertEquals(20, rowCount.get());
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