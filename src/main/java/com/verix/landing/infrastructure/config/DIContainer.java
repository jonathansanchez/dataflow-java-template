package com.verix.landing.infrastructure.config;

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

        DIContainer container = new DIContainer();

        container.register("job_options", options);
        container.register("pipeline", Pipeline.create(container.resolve("job_options")));


    }
}
