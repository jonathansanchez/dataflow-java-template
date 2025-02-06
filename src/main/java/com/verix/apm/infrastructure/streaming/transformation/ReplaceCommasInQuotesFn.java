package com.verix.apm.infrastructure.streaming.transformation;

import org.apache.beam.sdk.transforms.DoFn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceCommasInQuotesFn extends DoFn<String, String> {

    private static final String COMMA = ",";
    private static final String REPLACEMENT_CHAR = " +";
    private static final String QUOTED_PATTERN = "\"([^\"]*)\"";
    private static final String EMPTY_STRING = "";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<String> out) {
        String processedLine = replaceCommasInsideQuotes(line);
        processedLine = replaceEmptyCellsWithNull(processedLine);
        out.output(processedLine);
    }

    // Metodo para reemplazar las comas dentro de las comillas
    private String replaceCommasInsideQuotes(String line) {
        // Comprobamos si la línea tiene comillas dobles
        if (line.contains("\"")) {
            Matcher matcher = Pattern.compile(QUOTED_PATTERN).matcher(line);
            StringBuffer result = new StringBuffer();

            while (matcher.find()) {
                String quotedValue = matcher.group(1);
                quotedValue = quotedValue.replace(COMMA, REPLACEMENT_CHAR);
                matcher.appendReplacement(result, Matcher.quoteReplacement(quotedValue));
            }
            matcher.appendTail(result);
            return result.toString();
        } else {
            return line;
        }
    }


    private String replaceEmptyCellsWithNull(String line) {
        String[] columns = line.split(COMMA);

        for (int i = 0; i < columns.length; i++) {
            if (columns[i].trim().isEmpty()) {
                columns[i] = null;
            }
        }
        return String.join(COMMA, columns);
    }
}
