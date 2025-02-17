package com.verix.forecast.infrastructure.config;

import com.google.api.services.bigquery.model.TableSchema;
import com.verix.forecast.application.service.ForecastService;
import com.verix.forecast.application.service.StreamingService;
import com.verix.forecast.infrastructure.repository.BigQueryRepository;
import com.verix.forecast.infrastructure.repository.model.PortfolioTableSchema;
import com.verix.forecast.infrastructure.streaming.BeamDataPipelineReader;
import com.verix.forecast.infrastructure.streaming.BeamDataPipelineWriter;
import com.verix.forecast.infrastructure.streaming.transformation.PortfolioToTableRow;
import com.verix.forecast.infrastructure.streaming.transformation.TableRowToComponent;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class DIContainer {

    private final Map<String, Object> instances = new HashMap<>();

    public <T> void register(String type, T instance) {
        instances.put(type, instance);
    }

    public <T> T resolve(String type) {
        return Optional
                .ofNullable((T) instances.get(type))
                .orElseThrow(() -> new IllegalArgumentException("No registered instance: " + type));
    }

    public static void main(String[] args) {
        PipelineOptionsFactory.register(JobOptions.class);

        DIContainer container = new DIContainer();
        JobOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(JobOptions.class);

        container.register("job_options", options);
        container.register("table_schema", new TableSchema());
        container.register("portfolio_table_schema", new PortfolioTableSchema(container.resolve("table_schema")));
        container.register("big_query_repository", new BigQueryRepository(container.resolve("job_options"), container.resolve("portfolio_table_schema")));
        container.register("pipeline_reader", Pipeline.create(container.resolve("job_options")));
        container.register("pipeline_writer", Pipeline.create(container.resolve("job_options")));
        container.register("portfolio_to_table_row_transformation", new PortfolioToTableRow());
        container.register("table_row_to_component_transformation", new TableRowToComponent());

        container.register("apache_beam_pipeline_writer", new BeamDataPipelineWriter(
                container.resolve("pipeline_writer"),
                container.resolve("big_query_repository"),
                container.resolve("portfolio_to_table_row_transformation"))
        );
        container.register("forecast_service", new ForecastService(container.resolve("apache_beam_pipeline_writer")));
        container.register("apache_beam_pipeline_reader", new BeamDataPipelineReader(
                container.resolve("pipeline_reader"),
                container.resolve("big_query_repository"),
                container.resolve("table_row_to_component_transformation"),
                container.resolve("forecast_service"))
        );
        container.register("streaming_service", new StreamingService(container.resolve("apache_beam_pipeline_reader")));

        //Init
        StreamingService streamingService = container.resolve("streaming_service");
        streamingService.execute();
    }
}
