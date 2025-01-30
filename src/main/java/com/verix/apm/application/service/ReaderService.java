package com.verix.apm.application.service;

import com.verix.apm.domain.model.Apm;
import com.verix.apm.domain.model.ReaderRepository;
import com.verix.apm.domain.model.exception.ApmNotFoundException;

import java.util.List;
import java.util.Optional;

/*
- La clase ReaderService depende de la interfaz ReaderRepository
- permite acceder a los datos de tipo Apm
- ReaderService usa la interfaz ReaderRepository para acceder a los datos
*/

public class ReaderService {
    private final ReaderRepository repository;

    public ReaderService(ReaderRepository repository) {

        this.repository = repository;
    }

    public List<Apm> execute() {
        return Optional
                .ofNullable(repository.findAll())
                .orElseThrow(ApmNotFoundException::thrown); // Si la lista devuelta es null, se lanza una excepción
    }
}