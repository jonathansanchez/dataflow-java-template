package com.verix.landing.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableSchema;
import com.verix.landing.infrastructure.config.JobOptions;
import com.verix.landing.infrastructure.repository.BigQueryRepository;
import com.verix.landing.infrastructure.repository.model.LandingTableSchema;
import com.verix.landing.infrastructure.streaming.transformations.ConvertLandingToTableRow;
import com.verix.landing.infrastructure.streaming.transformations.ConvertStringToLanding;
import com.verix.landing.infrastructure.streaming.transformations.RemoveLineBreaks;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.google.cloud.bigquery.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LandingPipelineIntegrationTest {

    private static final String PROJECT_ID      = "PROJECT_ID";
    private static final String BUCKET_NAME     = "BUCKET_NAME";
    private static final String TEMP_LOCATION   = "gs://" + BUCKET_NAME + "/temp-files";
    private static final String CSV_FILE_NAME   = "CSV_FILE_NAME.csv";
    private static final String INPUT_FILE      = "gs://" + BUCKET_NAME + "/data/" + CSV_FILE_NAME;
    private static final String DATASET_NAME    = "DATASET_NAME";
    private static final String TABLE_NAME      = "TABLE_NAME";

    private static JobOptions jobOptions;
    private static Pipeline pipeline;
    private static LandingTableSchema schema;
    private static BigQueryRepository bigQueryRepository;
    private static ConvertStringToLanding convertStringToLanding;
    private static ConvertLandingToTableRow convertLandingToTableRow;
    private static RemoveLineBreaks removeLineBreaks;
    private static BigQuery bigQuery;
    private static TableId tableId;

    @BeforeAll
    public static void setUp() throws IOException {
        PipelineOptionsFactory.register(JobOptions.class);
        jobOptions = PipelineOptionsFactory.as(JobOptions.class);
        pipeline = Pipeline.create(jobOptions);
        schema = new LandingTableSchema(new TableSchema());
        bigQueryRepository = new BigQueryRepository(jobOptions, schema);
        convertStringToLanding = new ConvertStringToLanding();
        convertLandingToTableRow = new ConvertLandingToTableRow();
        removeLineBreaks = new RemoveLineBreaks();
        bigQuery = BigQueryOptions.getDefaultInstance().getService();

        jobOptions.setInput(INPUT_FILE);
        jobOptions.setOutputTable(String.format("%s:%s.%s", PROJECT_ID, DATASET_NAME, TABLE_NAME));
        jobOptions.setTempBucket(TEMP_LOCATION);
        tableId = TableId.of(DATASET_NAME, TABLE_NAME);

        // Delete table before insert
        tearDown();

        // Create a new table in BigQuery
        Schema schema = Schema.of(
                Field.of("component_id", StandardSQLTypeName.STRING),
                Field.of("apm_code", StandardSQLTypeName.STRING),
                Field.of("app_name", StandardSQLTypeName.STRING),
                Field.of("vendor", StandardSQLTypeName.STRING),
                Field.of("sw_type", StandardSQLTypeName.STRING),
                Field.of("sw_name", StandardSQLTypeName.STRING),
                Field.of("sw_id", StandardSQLTypeName.INT64),
                Field.of("sw_version", StandardSQLTypeName.STRING),
                Field.of("sw_expire_in", StandardSQLTypeName.STRING),
                Field.of("group_head_name", StandardSQLTypeName.STRING),
                Field.of("business_lines", StandardSQLTypeName.STRING),
                Field.of("dbr_tier", StandardSQLTypeName.STRING),
                Field.of("sw_valid_plan", StandardSQLTypeName.BOOL),
                Field.of("app_valid_plan", StandardSQLTypeName.BOOL),
                Field.of("sw_plan_status", StandardSQLTypeName.STRING),
                Field.of("plan_number", StandardSQLTypeName.INT64),
                Field.of("plan_name", StandardSQLTypeName.STRING),
                Field.of("plan_start_date", StandardSQLTypeName.DATE),
                Field.of("plan_finish_date", StandardSQLTypeName.DATE),
                Field.of("plan_funded", StandardSQLTypeName.BOOL),
                Field.of("ref_number", StandardSQLTypeName.STRING),
                Field.of("plan_comments", StandardSQLTypeName.STRING),
                Field.of("plan_external_cost", StandardSQLTypeName.INT64),
                Field.of("plan_internal_cost", StandardSQLTypeName.INT64),
                Field.of("plan_license_cost", StandardSQLTypeName.INT64),
                Field.of("eos_date", StandardSQLTypeName.DATE),
                Field.of("extended_date", StandardSQLTypeName.DATE),
                Field.of("extended_custom_date", StandardSQLTypeName.DATE),
                Field.of("local_rcmp", StandardSQLTypeName.BOOL),
                Field.of("country_name", StandardSQLTypeName.STRING),
                Field.of("internet_facing", StandardSQLTypeName.BOOL),
                Field.of("us_flag", StandardSQLTypeName.BOOL),
                Field.of("lifecycle", StandardSQLTypeName.STRING),
                Field.of("environments", StandardSQLTypeName.STRING)
        );

        TableDefinition tableDefinition = StandardTableDefinition.of(schema);
        TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();
        bigQuery.create(tableInfo);
    }

    @Test
    void run(){
        LandingPipeline landingPipeline = new LandingPipeline(
            jobOptions, pipeline, convertStringToLanding, convertLandingToTableRow, removeLineBreaks, bigQueryRepository
        );
        landingPipeline.run();
        AtomicInteger rowCount = countResult();
        assertEquals(10, rowCount.get());

    }

    @NotNull
    private static AtomicInteger countResult() {
        TableResult result     = bigQuery.listTableData(tableId, BigQuery.TableDataListOption.pageSize(10));
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
        bigQuery.delete(tableId);
    }
}
