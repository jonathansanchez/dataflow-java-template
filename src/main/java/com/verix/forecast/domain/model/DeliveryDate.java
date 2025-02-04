package com.verix.forecast.domain.model;

import com.verix.forecast.domain.model.exception.InvalidDateException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

public class DeliveryDate implements Serializable {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ROOT);
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ROOT);
    private static final String REGEX_SPECIAL_CHARS = "[^0-9-]";
    private static final String EMPTY_STRING = "";

    private final String value;

    private DeliveryDate(String value) {
        this.value = value;
    }

    public static DeliveryDate create(String value) {
        return new DeliveryDate(extractDate(value));
    }

    private static String extractDate(String value) {
        String optionalValue = Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .orElseThrow(InvalidDateException::thrown)
                .trim()
                .replaceAll(REGEX_SPECIAL_CHARS, EMPTY_STRING);

        if (optionalValue.isEmpty()) {
            throw InvalidDateException.thrown();
        }

        LocalDate date = LocalDate.parse(optionalValue, INPUT_FORMAT);
        return date.format(OUTPUT_FORMAT);
    }

    public String getValue() {
        return value;
    }
}
