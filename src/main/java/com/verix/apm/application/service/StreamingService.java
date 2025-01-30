package com.verix.apm.application.service;

import com.verix.apm.domain.model.DataPipeline;

/*
- servicio de ejecución de un pipeline de datos
- Está diseñada para trabajar con objetos que implementan la interfaz DataPipeline.
- el metodo de la clase execute() invoca el metodo run() del objeto DataPipeline.
- cuando llamas a execute(), se está ejecutando el pipeline de procesamiento de datos,
 */
public class StreamingService {
    private final DataPipeline pipeline;

    public StreamingService(DataPipeline pipeline) {

        this.pipeline = pipeline;
    }

    public void execute() {
        pipeline.run();
    }
}
