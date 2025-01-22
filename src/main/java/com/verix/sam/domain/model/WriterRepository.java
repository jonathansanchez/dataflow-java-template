package com.verix.sam.domain.model;

import java.util.List;

public interface WriterRepository {
    void save(List<Sam> samList);
}
