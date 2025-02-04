package com.verix.apm.infrastructure.config;

import com.google.api.services.bigquery.model.TableSchema;
import com.verix.apm.application.service.StreamingService;
import com.verix.apm.infrastructure.repository.model.ApmTableSchema;
import com.verix.apm.infrastructure.repository.model.BigQueryRepository;
import com.verix.apm.infrastructure.streaming.ApacheBeamDataPipeline;
import com.verix.apm.infrastructure.streaming.transformation.ApmToTableRow;
import com.verix.apm.infrastructure.streaming.transformation.RemoveLineBreaksTransformation;
import com.verix.apm.infrastructure.streaming.transformation.ReplaceCommasInQuotesFn;
import com.verix.apm.infrastructure.streaming.transformation.StringToApmTransformation;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
- contenedor de inyección de dependencias (DI)
- centraliza y gestiona todas las dependencias de la aplicación.
- metodo register: Registra instancias
- metodo resolve: Recupera instancias
 */
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

        options.setInput(System.getenv("INPUT_PATH"));
        options.setOutputTable(System.getenv("GCP_TABLE"));
        options.setTempBucket(System.getenv("TEMP_LOCATION"));

        printPipelineOptions(options);

        container.register("job_options", options);
        container.register("table_schema", new TableSchema());
        container.register("apm_table_schema", new ApmTableSchema(container.resolve("table_schema")));
        container.register("big_query_repository", new BigQueryRepository(container.resolve("job_options"), container.resolve("apm_table_schema")));
        container.register("pipeline", Pipeline.create(container.resolve("job_options")));
        //container.register("writer_service", new WriterService(container.resolve("big_query_repository")));
        //container.register("remove_line_breaks_transformation", new RemoveLineBreaksTransformation());
        container.register("replace_commas_in_quotes_fn", new ReplaceCommasInQuotesFn());
        container.register("string_to_apm_transformation", new StringToApmTransformation());
        container.register("apm_To_TableRow", new ApmToTableRow());
        container.register("apache_beam_pipeline", new ApacheBeamDataPipeline(container.resolve("job_options"), container.resolve("pipeline"),container.resolve("replace_commas_in_quotes_fn"), container.resolve("string_to_apm_transformation"),container.resolve("apm_To_TableRow"), container.resolve("big_query_repository")));
        container.register("streaming_service", new StreamingService(container.resolve("apache_beam_pipeline")));

        //Init
        StreamingService streamingService = container.resolve("streaming_service");
        streamingService.execute();
    }

    private static void  printPipelineOptions(JobOptions options) {
        System.out.println("Input: " + options.getInput());
        System.out.println("OutputTable: " + options.getOutputTable());
        System.out.println("TempBucket: " + options.getTempBucket());
    }
}

