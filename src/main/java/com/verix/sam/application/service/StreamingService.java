package com.verix.sam.application.service;

import com.verix.sam.domain.model.DataPipeline;

public class StreamingService {
    private final DataPipeline pipeline;

    public StreamingService(DataPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public void execute() {
        pipeline.run();
    }
}
