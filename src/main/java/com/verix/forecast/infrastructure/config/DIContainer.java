package com.verix.forecast.infrastructure.config;

import com.google.api.services.bigquery.model.TableSchema;
import com.verix.forecast.application.service.StreamingService;
import com.verix.forecast.infrastructure.repository.BigQueryRepository;
import com.verix.forecast.infrastructure.repository.model.RemediationTableSchema;
import com.verix.forecast.infrastructure.streaming.BeamDataPipeline;
import com.verix.forecast.infrastructure.streaming.transformation.RemediationToTableRow;
import com.verix.forecast.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.forecast.infrastructure.streaming.transformation.StringToRemediationTransformation;
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
        container.register("remediation_table_schema", new RemediationTableSchema(container.resolve("table_schema")));
        container.register("big_query_repository", new BigQueryRepository(container.resolve("job_options"), container.resolve("remediation_table_schema")));
        container.register("pipeline", Pipeline.create(container.resolve("job_options")));
        container.register("remove_line_breaks_transformation", new RemoveLineBreaksTransformation());
        container.register("string_to_remediation_transformation", new StringToRemediationTransformation());
        container.register("remediation_to_table_row_transformation", new RemediationToTableRow());
        container.register("apache_beam_pipeline", new BeamDataPipeline(
                container.resolve("job_options"),
                container.resolve("pipeline"),
                container.resolve("big_query_repository"),
                container.resolve("remove_line_breaks_transformation"),
                container.resolve("string_to_remediation_transformation"),
                container.resolve("remediation_to_table_row_transformation"))
        );
        container.register("streaming_service", new StreamingService(container.resolve("apache_beam_pipeline")));

        //Init
        StreamingService streamingService = container.resolve("streaming_service");
        streamingService.execute();
    }
}
