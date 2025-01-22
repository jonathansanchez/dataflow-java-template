package com.verix.sam.infrastructure.config;

import com.google.api.services.bigquery.model.TableSchema;
import com.verix.sam.application.service.ReaderService;
import com.verix.sam.application.service.WriterService;
import com.verix.sam.infrastructure.repository.BigQueryWriterRepository;
import com.verix.sam.infrastructure.repository.CloudStorageRepository;
import com.verix.sam.infrastructure.repository.model.SamTableSchema;
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
        container.register("job_options", PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(JobOptions.class));
        container.register("cloud_storage_repository", new CloudStorageRepository(container.resolve("job_options")));
        container.register("table_schema", new TableSchema());
        container.register("sam_table_schema", new SamTableSchema(container.resolve("table_schema")));
        container.register("big_query_repository", new BigQueryWriterRepository(container.resolve("job_options"), container.resolve("sam_table_schema")));
        container.register("reader_service", new ReaderService(container.resolve("cloud_storage_repository")));
        container.register("writer_service", new WriterService(container.resolve("big_query_repository")));
    }
}
