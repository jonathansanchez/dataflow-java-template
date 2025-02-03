package com.verix.landing.infrastructure.config;

import com.google.api.services.bigquery.model.TableSchema;
import com.verix.landing.application.service.StreamingService;
import com.verix.landing.infrastructure.repository.BigQueryRepository;
import com.verix.landing.infrastructure.repository.model.LandingTableSchema;
import com.verix.landing.infrastructure.streaming.LandingPipeline;
import com.verix.landing.infrastructure.streaming.transformations.ConvertLandingToTableRow;
import com.verix.landing.infrastructure.streaming.transformations.ConvertStringToLanding;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DIContainer {
    private final Map<String, Object> instances = new HashMap<>();

    public <T> void register(String key, T value) {
        instances.put(key, value);
    }

    public <T> T resolve(String type){
        return Optional
                .ofNullable((T) instances.get(type))
                .orElseThrow(() -> new IllegalArgumentException("No registered instance: " + type));
    }

    public static void main(String[] args) {
        PipelineOptionsFactory.register(JobOptions.class);
        JobOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(JobOptions.class);

        options.setInput("gs://bucket-swc-test/landing/data/landing.csv");
        options.setOutputTable("dataset_swc_test.landing");
        options.setTempBucket("gs://bucket-swc-test/landing/temp-files");

        DIContainer container = new DIContainer();

        container.register("job_options", options);
        container.register("pipeline", Pipeline.create(container.resolve("job_options")));
        container.register("convert_to_landing_transformation", new ConvertStringToLanding());
        container.register("convert_to_table_row_transformation", new ConvertLandingToTableRow());
        container.register("table_schema", new TableSchema());
        container.register("landing_table_schema", new LandingTableSchema(container.resolve("table_schema")));
        container.register("bigquery_repository", new BigQueryRepository(container.resolve("job_options"), container.resolve("landing_table_schema")));
        container.register("landing_pipeline", new LandingPipeline(container.resolve("job_options"), container.resolve("pipeline"), container.resolve("convert_to_landing_transformation"), container.resolve("convert_to_table_row_transformation"), container.resolve("bigquery_repository")));
        container.register("streaming_service", new StreamingService(container.resolve("landing_pipeline")));

        //Init
        StreamingService streamingService = container.resolve("streaming_service");
        streamingService.execute();

    }
}
