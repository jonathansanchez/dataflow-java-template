package com.verix.apm.infrastructure.streaming.transformation;

import com.verix.apm.domain.model.BooleanCleaner;
import com.verix.apm.domain.model.LifeDate;
import com.verix.apm.domain.model.Apm;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
- Toma un String, lo divide por commas y lo convierte a objeto Apm
 */
public class StringToApmTransformation extends DoFn<String, Apm> {

    private static final String COMMA = ",";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<Apm> out) {
        //System.out.println("Line: " + line);

        List<String> splitValue = Optional
                .ofNullable(line) // Evita errores si la línea es null
                .map(s -> Arrays.asList(s.trim().split(COMMA))) // Separa por comas
                .orElseThrow(RuntimeException::new); // Si la línea es null, lanza excepción

        //System.out.println("Split Values: " + splitValue);

/*        // Imprimir valores después de conversión
        System.out.println("✅ Valores convertidos:");
        System.out.println(" - isCompliant: " + BooleanCleaner.parseBoolean(splitValue.get(2)));
        System.out.println(" - cia: " + BooleanCleaner.parseBoolean(splitValue.get(3)));
        System.out.println(" - applicationTested: " + BooleanCleaner.parseBoolean(splitValue.get(8)));*/

        out.output(new Apm(
                splitValue.get(0), //apmCode
                nullIfEmpty(splitValue.get(1).toUpperCase()), //apmName
                splitValue.get(2),  // isCompliant
                splitValue.get(3),  // cia
                nullIfEmpty(splitValue.get(4)), // lcState
                LifeDate.create(splitValue.get(5)), // productionDate
                LifeDate.create(splitValue.get(6)), // retirementDate
                splitValue.get(7), // dbrRating
                splitValue.get(8),  // applicationTested
                splitValue.get(9), // applicationContact
                nullIfEmpty(splitValue.get(10)), // manager
                nullIfEmpty(splitValue.get(11)), // vp
                nullIfEmpty(splitValue.get(12)), // svp
                nullIfEmpty(splitValue.get(13)), // portfolioOwner
                nullIfEmpty(splitValue.get(14))  // iso
                )
        );
    }

    private String nullIfEmpty(String value) {
        return (value == null // 1️⃣ Si el valor es nulo
                || value.trim().isEmpty() // 2️⃣ Si el valor es una cadena vacía o solo contiene espacios en blanco
                || "null".equalsIgnoreCase(value.trim())) // 3️⃣ Si el valor es la palabra "null" (sin importar mayúsculas o minúsculas)
                ? null // ✅ Devuelve `null` en estos casos
                : value; // 🚀 Si no, devuelve el mismo valor original
    }
}