package com.verix.apm.domain.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

public class LifeDate {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final String value;

    private LifeDate(String value) {
        this.value = value;
    }

    //Llama a extractDate(value) para limpiar y formatear la fecha antes de guardarla.
    public static LifeDate create(String value) {
        return new LifeDate(extractDate(value));
    }

    private static String extractDate(String value) {
        return Optional.ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .map(s -> LocalDate.parse(s.trim(), INPUT_FORMAT).format(OUTPUT_FORMAT)) // Devuelve formato yyyy-MM-dd
                .orElse(null);
    }

    //Devuelve la fecha formateada
    public String getValue() {
        return value;
    }
}

