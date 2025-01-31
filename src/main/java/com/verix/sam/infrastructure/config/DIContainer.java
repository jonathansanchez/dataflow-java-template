package com.verix.sam.infrastructure.config;

import com.google.api.services.bigquery.model.TableSchema;
import com.verix.sam.application.service.StreamingService;
import com.verix.sam.infrastructure.repository.BigQueryRepository;
import com.verix.sam.infrastructure.repository.model.SamTableSchema;
import com.verix.sam.infrastructure.streaming.ApacheBeamDataPipeline;
import com.verix.sam.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.sam.infrastructure.streaming.transformation.SamToTableRow;
import com.verix.sam.infrastructure.streaming.transformation.StringToSamTransformation;
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
        container.register("sam_table_schema", new SamTableSchema(container.resolve("table_schema")));
        container.register("big_query_repository", new BigQueryRepository(container.resolve("job_options"), container.resolve("sam_table_schema")));
        container.register("pipeline", Pipeline.create(container.resolve("job_options")));
        container.register("remove_line_breaks_transformation", new RemoveLineBreaksTransformation());
        container.register("string_to_sam_transformation", new StringToSamTransformation());
        container.register("sam_to_table_row_transformation", new SamToTableRow());
        container.register("apache_beam_pipeline", new ApacheBeamDataPipeline(
                container.resolve("job_options"),
                container.resolve("pipeline"),
                container.resolve("big_query_repository"),
                container.resolve("remove_line_breaks_transformation"),
                container.resolve("string_to_sam_transformation"),
                container.resolve("sam_to_table_row_transformation"))
        );
        container.register("streaming_service", new StreamingService(container.resolve("apache_beam_pipeline")));

        //Init
        StreamingService streamingService = container.resolve("streaming_service");
        streamingService.execute();
    }
}
