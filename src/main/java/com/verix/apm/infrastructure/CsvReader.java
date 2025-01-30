package com.verix.apm.infrastructure;

import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.values.PCollection;

public class CsvReader {
    public static PCollection<String> readCsvFile(Pipeline pipeline, String inputPath) {
        return pipeline.apply("Extract: Read CSV File", TextIO.read().withSkipHeaderLines(1).from(inputPath));
    }
}

