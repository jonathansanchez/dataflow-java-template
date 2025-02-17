package com.verix.forecast.infrastructure.streaming;

import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.cloud.bigquery.*;
import com.verix.forecast.application.service.ForecastService;
import com.verix.forecast.domain.model.Component;
import com.verix.forecast.domain.model.DataPipelineReader;
import com.verix.forecast.domain.model.Portfolio;
import com.verix.forecast.infrastructure.config.JobOptions;
import com.verix.forecast.infrastructure.repository.BigQueryRepository;
import com.verix.forecast.infrastructure.repository.model.PortfolioTableSchema;
import com.verix.forecast.infrastructure.streaming.transformation.ComponentToListTransformation;
import com.verix.forecast.infrastructure.streaming.transformation.PortfolioToTableRow;
import com.verix.forecast.infrastructure.streaming.transformation.TableRowToComponent;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BeamDataPipelineReaderIntegrationTest implements Serializable {

    private static final String PROJECT_ID    = System.getenv("PROJECT_ID");
    private static final String BUCKET_NAME   = System.getenv("BUCKET_NAME");
    private static final String TEMP_LOCATION = "gs://" + BUCKET_NAME + "/temp-files";
    private static final String DATASET_NAME  = System.getenv("DATASET_NAME");
    private static final String TABLE_IN      = System.getenv("TABLE_IN");
    private static final String TABLE_OUT     = System.getenv("TABLE_OUT");
    private static final String COUNTRY       = System.getenv("COUNTRY");


    private static JobOptions options;
    private static BigQuery bigQuery;
    private static TableRowToComponent tableRowToComponent;
    private static PortfolioToTableRow portfolioToTableRow;
    private static PortfolioTableSchema portfolioTableSchema;
    private static BigQueryRepository bigQueryRepository;
    private static BeamDataPipelineWriter pipelineWriter;
    private static ForecastService forecastService;

    @BeforeAll
    public static void setUp() throws IOException {
        PipelineOptionsFactory.register(JobOptions.class);
        options = PipelineOptionsFactory.as(JobOptions.class);
        options.setTempLocation(TEMP_LOCATION);
        options.setCountry(COUNTRY);
        options.setInput(String.format("%s.%s.%s", PROJECT_ID, DATASET_NAME, TABLE_IN));
        options.setOutput(String.format("%s.%s.%s", PROJECT_ID, DATASET_NAME, TABLE_OUT));

        bigQuery             = BigQueryOptions.getDefaultInstance().getService();
        tableRowToComponent  = new TableRowToComponent();
        portfolioToTableRow  = new PortfolioToTableRow();
        portfolioTableSchema = new PortfolioTableSchema(new TableSchema());
        bigQueryRepository   = new BigQueryRepository(options, portfolioTableSchema);
        pipelineWriter       = new BeamDataPipelineWriter(Pipeline.create(options), bigQueryRepository, portfolioToTableRow);
        forecastService      = new ForecastService(pipelineWriter);


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
        DataPipelineReader pipeline = new BeamDataPipelineReader(Pipeline.create(options),
                bigQueryRepository,
                tableRowToComponent,
                forecastService);

        pipeline.run();
    }

    @Test
        void run_reader() throws InterruptedException {
        Pipeline pipeline = Pipeline.create(options);

        PCollection<TableRow> data = pipeline.apply("Extract: Read from BigQuery", bigQueryRepository.readLanding());

        PCollection<Component> components = data.apply("Transform: Format from String to Component Class", ParDo.of(tableRowToComponent));

        List<Component> componentList = new ArrayList<>();

        components.apply("Transform: Component Class into List", ParDo.of(new ComponentToListTransformation<>(componentList)));


        pipeline.run().waitUntilFinish();
        componentList.forEach(component -> System.out.println(component.toString()));
        List<Portfolio> portfolios = forecastService.execute(componentList);
        portfolios.forEach(portfolio -> System.out.println(portfolio.toString()));
        assertNotNull(portfolios);
    }

    @AfterAll
    public static void tearDown() {
        // Delete table from BigQuery
        bigQuery.delete(TableId.of(PROJECT_ID, DATASET_NAME, TABLE_OUT));
    }
}