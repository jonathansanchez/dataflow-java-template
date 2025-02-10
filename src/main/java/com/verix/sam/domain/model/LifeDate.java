package com.verix.sam.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

public class LifeDate implements Serializable {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a").withLocale(Locale.ROOT);
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ROOT);
    private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s.-:/]";
    private static final String EMPTY_STRING = "";

    private final String value;

    private LifeDate(String value) {
        this.value = value;
    }

    public static LifeDate create(String value) {
        return new LifeDate(extractDate(value));
    }

    private static String extractDate(String value) {
        return Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .map(s ->
                        s
                                .trim()
                                .replaceAll(REGEX_SPECIAL_CHARS, EMPTY_STRING)
                )
                .filter(Predicate.not(String::isEmpty))
                .map(s -> {
                    LocalDate date = LocalDate.parse(s, INPUT_FORMAT);
                    return date.format(OUTPUT_FORMAT);
                })
                .orElse(null);
    }

    public String getValue() {
        return value;
    }
}
