package com.verix.apm.domain.model;

/*
-  interfaz que define cómo guardar los objetos Apm
 */
public interface WriterRepository {
    void save(Apm apm);
}
