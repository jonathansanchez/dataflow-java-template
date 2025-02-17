package com.verix.forecast.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.cloud.bigquery.*;
import com.verix.forecast.domain.model.DataPipelineWriter;
import com.verix.forecast.domain.model.Portfolio;
import com.verix.forecast.infrastructure.config.JobOptions;
import com.verix.forecast.infrastructure.repository.BigQueryRepository;
import com.verix.forecast.infrastructure.repository.model.PortfolioTableSchema;
import com.verix.forecast.infrastructure.streaming.transformation.PortfolioToTableRow;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

class BeamDataPipelineWriterIntegrationTest implements Serializable {

    private static final String PROJECT_ID    = System.getenv("PROJECT_ID");
    private static final String BUCKET_NAME   = System.getenv("BUCKET_NAME");
    private static final String TEMP_LOCATION = "gs://" + BUCKET_NAME + "/temp-files";
    private static final String DATASET_NAME  = System.getenv("DATASET_NAME");
    private static final String TABLE_IN      = System.getenv("TABLE_IN");
    private static final String TABLE_OUT     = System.getenv("TABLE_OUT");
    private static final String COUNTRY       = System.getenv("COUNTRY");


    private static JobOptions options;
    private static BigQuery bigQuery;
    private static PortfolioToTableRow portfolioToTableRow;
    private static PortfolioTableSchema portfolioTableSchema;
    private static BigQueryRepository bigQueryRepository;

    @BeforeAll
    public static void setUp() throws IOException {
        PipelineOptionsFactory.register(JobOptions.class);
        options = PipelineOptionsFactory.as(JobOptions.class);
        options.setTempLocation(TEMP_LOCATION);
        options.setCountry(COUNTRY);
        options.setInput(String.format("%s.%s.%s", PROJECT_ID, DATASET_NAME, TABLE_IN));
        options.setOutput(String.format("%s.%s.%s", PROJECT_ID, DATASET_NAME, TABLE_OUT));

        bigQuery             = BigQueryOptions.getDefaultInstance().getService();
        portfolioToTableRow  = new PortfolioToTableRow();
        portfolioTableSchema = new PortfolioTableSchema(new TableSchema());
        bigQueryRepository   = new BigQueryRepository(options, portfolioTableSchema);


        // Delete table before insert
        bigQuery.delete(TableId.of(PROJECT_ID, DATASET_NAME, TABLE_OUT));

        // Create a new table in BigQuery
        TableId tableId = TableId.of(PROJECT_ID, DATASET_NAME, TABLE_OUT);
        Schema schema = Schema.of(
                Field.of("country", StandardSQLTypeName.STRING),
                Field.of("portfolio_date", StandardSQLTypeName.DATE),
                Field.of("expired", StandardSQLTypeName.INT64),
                Field.of("expired_kri", StandardSQLTypeName.NUMERIC),
                Field.of("expiring", StandardSQLTypeName.INT64),
                Field.of("expiring_kri", StandardSQLTypeName.NUMERIC),
                Field.of("total", StandardSQLTypeName.INT64),
                Field.of("created_at", StandardSQLTypeName.DATE)
        );

        StandardTableDefinition tableDefinition = StandardTableDefinition.newBuilder().setSchema(schema).build();
        TableInfo tableInfo = TableInfo.newBuilder(tableId, tableDefinition).build();
        bigQuery.create(tableInfo);
    }

    @Test
    void run() {
        DataPipelineWriter pipeline = new BeamDataPipelineWriter(Pipeline.create(options),
                bigQueryRepository,
                portfolioToTableRow);

        pipeline.run(List.of());
    }

    @Test
        void run_writer() throws InterruptedException {
        Pipeline pipeline = Pipeline.create(options);

        PCollection<Portfolio> components = pipeline.apply(Create.of(List.of()));

        PCollection<TableRow> tableRows = components.apply("Transform: Format from Component Class to Table Row Class", ParDo.of(portfolioToTableRow));

        tableRows.apply("Load: Write Portfolio into BigQuery", bigQueryRepository.writeToBigQuery());

        pipeline.run();
    }

    @AfterAll
    public static void tearDown() {
        // Delete table from BigQuery
        bigQuery.delete(TableId.of(PROJECT_ID, DATASET_NAME, TABLE_OUT));
    }
}