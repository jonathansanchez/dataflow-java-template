package com.verix.landing.infrastructure.streaming.transformations;

import org.apache.beam.sdk.transforms.DoFn;

public class RemoveLineBreaks extends DoFn<String, String> {

    private static final String REGEX_LINE_BREAK = "[\\r\\n]+";
    private static final String EMPTY_STRING = "";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<String> out) {
        String cleanedLine = line.replace(REGEX_LINE_BREAK, EMPTY_STRING);
        out.output(cleanedLine);
    }
}
