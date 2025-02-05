package com.verix.apm.infrastructure.streaming.transformation;

import org.apache.beam.sdk.transforms.DoFn;

/*
    Filtra las filas vacías o que solo contienen comas
 */
public class FilterEmptyRowsFn extends DoFn<String, String> {
    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<String> out) {
        if (!line.trim().isEmpty() && !line.matches("^,*$")) {
            out.output(line);
        }
    }
}

