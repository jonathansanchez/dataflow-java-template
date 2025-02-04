package com.verix.forecast.infrastructure.streaming.transformation;

import org.apache.beam.sdk.transforms.DoFn;

public class RemoveLineBreaksTransformation extends DoFn<String, String> {

    private static final String REGEX_LINE_BREAK = "[\\r\\n]+";
    public static final String EMPTY_STRING = "";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<String> out) {
        String cleanedLine = line.replaceAll(REGEX_LINE_BREAK, EMPTY_STRING).trim();
        out.output(cleanedLine);
    }
}
