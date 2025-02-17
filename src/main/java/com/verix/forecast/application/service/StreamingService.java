package com.verix.forecast.application.service;

import com.verix.forecast.domain.model.DataPipelineReader;

public class StreamingService {
    private final DataPipelineReader pipeline;

    public StreamingService(DataPipelineReader pipeline) {
        this.pipeline = pipeline;
    }

    public void execute() {
        pipeline.run();
    }
}
