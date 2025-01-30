package com.verix.apm.application.service;

import com.verix.apm.domain.model.Apm;
import com.verix.apm.domain.model.WriterRepository;
import org.apache.beam.sdk.transforms.DoFn;

/*
- actúa como un servicio de escritura que utiliza un repositorio para guardar los objetos Apm
- servicio que utiliza la interfaz para guardar los objetos Apm dentro del pipeline de Apache Beam.
- se encarga de procesar los elementos Apm, guardarlos usando el repositorio y luego pasar esos objetos al siguiente paso del pipeline.
 */
public class WriterService extends DoFn<Apm, Apm> {
    private final WriterRepository repository;

    public WriterService(WriterRepository repository) {

        this.repository = repository;
    }

    @ProcessElement
    public void execute(@Element Apm apm, OutputReceiver<Apm> out) {
        repository.save(apm);
        out.output(apm);
    }
}
