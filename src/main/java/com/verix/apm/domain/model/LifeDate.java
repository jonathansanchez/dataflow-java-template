package com.verix.apm.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

public class LifeDate implements Serializable {

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ROOT);
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String REGEX_CLEANUP = "[^a-zA-Z0-9-]";
    private static final String EMPTY_STRING = "";

    private final String value;

    private LifeDate(String value) {
        this.value = value;
    }

    public static LifeDate create(String value) {
        return new LifeDate(extractDate(value));
    }

    private static String extractDate(String value) {
        try {
            return Optional.ofNullable(value)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(s -> s.replaceAll(REGEX_CLEANUP, EMPTY_STRING))
                    .filter(s -> !s.isEmpty()) // Si después de limpiar queda vacío, devuelve null
                    .map(s -> LocalDate.parse(s, INPUT_FORMAT).format(OUTPUT_FORMAT))
                    .orElse(null);
        } catch (Exception e) {
            System.out.println("Error al convertir la fecha: " + value);
            return null;
        }
    }

    public String getValue() {
        return value;
    }
}
