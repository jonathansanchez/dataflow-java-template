package com.verix.sam.application.service;

import com.verix.sam.domain.model.WriterRepository;
import org.apache.beam.sdk.values.PCollection;

public class WriterService {
    private final WriterRepository repository;

    public WriterService(WriterRepository repository) {
        this.repository = repository;
    }

    public void execute(PCollection<String> samListString) {
        repository.save(samListString);
    }
}
