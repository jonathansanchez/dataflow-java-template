package com.verix.apm.infrastructure.streaming.transformation;

import org.apache.beam.sdk.transforms.DoFn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceCommasInQuotesFn extends DoFn<String, String> {

    private static final String COMMA = ",";
    private static final String REPLACEMENT_CHAR = " +";
    private static final String QUOTED_PATTERN = "\"([^\"]*)\""; // Expresión regular para encontrar contenido dentro de comillas
    private static final String EMPTY_STRING = "";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<String> out) {
        // Verificar y procesar la línea para reemplazar comas dentro de comillas y celdas vacías
        String processedLine = replaceCommasInsideQuotes(line);
        processedLine = replaceEmptyCellsWithNull(processedLine); // Reemplazar celdas vacías por null

        // Emitir la línea procesada
        out.output(processedLine);
    }

    // Metodo para reemplazar las comas dentro de las comillas
    private String replaceCommasInsideQuotes(String line) {
        // Comprobamos si la línea tiene comillas dobles
        if (line.contains("\"")) {
            Matcher matcher = Pattern.compile(QUOTED_PATTERN).matcher(line);
            StringBuffer result = new StringBuffer();

            // Reemplazamos las comas dentro de las comillas por "+"
            while (matcher.find()) {
                String quotedValue = matcher.group(1); // Valor dentro de las comillas
                quotedValue = quotedValue.replace(COMMA, REPLACEMENT_CHAR); // Reemplazamos las comas por "+"
                matcher.appendReplacement(result, Matcher.quoteReplacement(quotedValue));  // Eliminamos las comillas dobles
            }
            matcher.appendTail(result);
            return result.toString();
        } else {
            // Si no hay comillas dobles, devolvemos la línea tal cual
            return line;
        }
    }

    // Metodo para reemplazar celdas vacías por null
    private String replaceEmptyCellsWithNull(String line) {
        String[] columns = line.split(COMMA);

        // Reemplazar las celdas vacías por "null"
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].trim().isEmpty()) {
                columns[i] = "null";  // Puedes usar null o cualquier otro valor que consideres adecuado
            }
        }

        // Reconstruir la línea con las celdas vacías reemplazadas
        return String.join(COMMA, columns);
    }
}
