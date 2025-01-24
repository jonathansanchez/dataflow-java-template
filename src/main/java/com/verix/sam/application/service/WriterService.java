package com.verix.sam.application.service;

import com.verix.sam.domain.model.Sam;
import com.verix.sam.domain.model.WriterRepository;
import org.apache.beam.sdk.transforms.DoFn;

public class WriterService extends DoFn<Sam, Sam> {
    private final WriterRepository repository;

    public WriterService(WriterRepository repository) {
        this.repository = repository;
    }

    @ProcessElement
    public void execute(@Element Sam sam, OutputReceiver<Sam> out) {
        repository.save(sam);
        out.output(sam);
    }
}
