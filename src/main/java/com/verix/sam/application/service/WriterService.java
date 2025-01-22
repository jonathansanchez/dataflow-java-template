package com.verix.sam.application.service;

import com.verix.sam.domain.model.Sam;
import com.verix.sam.domain.model.WriterRepository;

import java.util.List;

public class WriterService {
    private final WriterRepository repository;

    public WriterService(WriterRepository repository) {
        this.repository = repository;
    }

    public void execute(List<Sam> samList) {
        repository.save(samList);
    }
}
