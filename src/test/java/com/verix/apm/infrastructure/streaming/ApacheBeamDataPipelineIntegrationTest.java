package com.verix.apm.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.cloud.bigquery.*;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.*;


import com.verix.apm.domain.model.Apm;
import com.verix.apm.infrastructure.config.JobOptions;
import com.verix.apm.infrastructure.repository.model.BigQueryRepository;
import com.verix.apm.infrastructure.repository.model.ApmTableSchema;
import com.verix.apm.infrastructure.streaming.transformation.*;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.jetbrains.annotations.NotNull;
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

    private static JobOptions jobOptions;
    private static BigQuery bigQuery;


    private static ApmTableSchema schema;
    private static FilterEmptyRowsFn filterEmptyRowsFn;
    private static ReplaceCommasInQuotesFn replaceCommasInQuotesFn;
    private static StringToApmTransformation stringToApmTransformation;
    private static ApmToTableRow apmToTableRow;
    private static BigQueryRepository bigQueryRepository;
    private static TableId tableId;

    @BeforeAll
    public static void setUp() throws IOException {
        System.out.println("Using TEMP_LOCATION: " + TEMP_LOCATION);
        System.out.println("Using INPUT_FILE: " + INPUT_FILE);

        PipelineOptionsFactory.register(JobOptions.class);
        jobOptions                      = PipelineOptionsFactory.as(JobOptions.class);
        bigQuery                        = BigQueryOptions.getDefaultInstance().getService();
        filterEmptyRowsFn               = new FilterEmptyRowsFn();
        replaceCommasInQuotesFn         = new ReplaceCommasInQuotesFn();
        stringToApmTransformation       = new StringToApmTransformation();
        schema                          = new ApmTableSchema(new TableSchema());
        apmToTableRow                   = new ApmToTableRow();
        bigQueryRepository              = new BigQueryRepository(jobOptions, schema);


        jobOptions.setInput(INPUT_FILE);
        jobOptions.setOutputTable(String.format("%s:%s.%s", PROJECT_ID, DATASET_NAME, TABLE_NAME));
        jobOptions.setTempBucket(TEMP_LOCATION);

        tableId = TableId.of(DATASET_NAME, TABLE_NAME);

        // Delete table before insert
        bigQuery.delete(TableId.of(PROJECT_ID, DATASET_NAME, TABLE_NAME));

        // Create a new table in BigQuery
        TableId tableId = TableId.of(PROJECT_ID, DATASET_NAME, TABLE_NAME);
        Schema schema = Schema.of(
                Field.of("apm_code", StandardSQLTypeName.STRING),
                Field.of("apm_name", StandardSQLTypeName.STRING),
                Field.of("is_compliant", StandardSQLTypeName.BOOL),
                Field.of("cia", StandardSQLTypeName.BOOL),
                Field.of("lc_state", StandardSQLTypeName.STRING),
                Field.of("production_date", StandardSQLTypeName.DATE),
                Field.of("retirement_date", StandardSQLTypeName.DATE),
                Field.of("dbr_rating", StandardSQLTypeName.STRING),
                Field.of("application_tested", StandardSQLTypeName.BOOL),
                Field.of("application_contact", StandardSQLTypeName.STRING),
                Field.of("manager", StandardSQLTypeName.STRING),
                Field.of("vp", StandardSQLTypeName.STRING),
                Field.of("svp", StandardSQLTypeName.STRING),
                Field.of("portfolio_owner", StandardSQLTypeName.STRING),
                Field.of("iso", StandardSQLTypeName.STRING),
                Field.of("country", StandardSQLTypeName.STRING)
        );
        TableDefinition tableDefinition = StandardTableDefinition.of(schema);
        TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();
        bigQuery.create(tableInfo);
    }

    @Test
    void run() {
        ApacheBeamDataPipeline pipeline = new ApacheBeamDataPipeline(
                jobOptions,
                Pipeline.create(jobOptions),
                filterEmptyRowsFn,
                replaceCommasInQuotesFn,
                stringToApmTransformation,
                apmToTableRow,
                bigQueryRepository);

        pipeline.run();
        AtomicInteger rowCount = countResult();
        assertEquals(32, rowCount.get());
    }

    @Test
    public void run_pipeline() {
        Pipeline pipeline = Pipeline.create(jobOptions);

        PCollection<String> rawData = pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(jobOptions.getInput()));

        PCollection<String> nonEmptyLines = rawData.apply("Filter: Remove empty rows", ParDo.of(filterEmptyRowsFn));

        PCollection<String> processedData = nonEmptyLines.apply("Transform: Replace commas inside quotes", ParDo.of(replaceCommasInQuotesFn));

        PCollection<Apm> apmList = processedData.apply("Transform: Format from String to APM Class and fields", ParDo.of(stringToApmTransformation));

        PCollection<TableRow> tableRows  = apmList.apply("Transform: Format from APM Class to Table Row Class",ParDo.of(apmToTableRow));

        tableRows.apply("Load: Write APM into BigQuery", bigQueryRepository.writeToBigQuery());

        pipeline.run().waitUntilFinish();

        // Count data from BigQuery
        AtomicInteger rowCount = countResult();
        assertEquals(32, rowCount.get());
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
}
