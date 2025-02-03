package com.verix.landing.application.service;

import com.verix.landing.domain.model.DataPipeline;

public class StreamingService {

    private final DataPipeline dataPipeline;

    public StreamingService(DataPipeline dataPipeline) {
        this.dataPipeline = dataPipeline;
    }

    public void execute(){
        dataPipeline.run();
    }
}
