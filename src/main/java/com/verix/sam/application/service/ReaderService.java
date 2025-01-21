package com.verix.sam.application.service;

import com.verix.sam.domain.model.ReaderRepository;
import com.verix.sam.domain.model.Sam;
import com.verix.sam.domain.model.exception.SamNotFoundException;

import java.util.List;
import java.util.Optional;

public class ReaderService {
    private final ReaderRepository repository;

    public ReaderService(ReaderRepository repository) {
        this.repository = repository;
    }

    public List<Sam> execute() {
        return Optional
                .ofNullable(repository.findAll())
                .orElseThrow(SamNotFoundException::thrown);
    }
}
