package com.verix.sam.domain.model;

import org.apache.beam.sdk.values.PCollection;

public interface WriterRepository {
    void save(PCollection<String> samListString);
}
