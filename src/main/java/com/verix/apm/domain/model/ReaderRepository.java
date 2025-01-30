package com.verix.apm.domain.model;

import java.util.List;

/*
- es la interfaz que define cómo acceder a los datos
 */
public interface ReaderRepository {
    List<Apm> findAll();
}
