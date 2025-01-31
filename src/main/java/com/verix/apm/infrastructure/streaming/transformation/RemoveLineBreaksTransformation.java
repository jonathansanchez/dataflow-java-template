package com.verix.apm.infrastructure.streaming.transformation;

import org.apache.beam.sdk.transforms.DoFn;

/*
- Elimina saltos de linea
 */
public class RemoveLineBreaksTransformation extends DoFn<String, String> {

    private static final String REGEX_LINE_BREAK = "[\\r\\n]+"; // Saltos de linea
    public static final String EMPTY_STRING = "";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<String> out) {
        String cleanedLine = line.replaceAll(REGEX_LINE_BREAK, EMPTY_STRING).trim();
        System.out.println("Cleaned Line: " + cleanedLine);
        out.output(cleanedLine); //emite una linea limpia
    }
}
