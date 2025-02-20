package com.verix.forecast.domain.model;

import com.verix.forecast.domain.model.exception.InvalidCountryException;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Predicate;

public class Country implements Serializable {
    private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z]";
    private static final String EMPTY_STRING = "";

    private final String value;

    private Country(String value) {
        this.value = value;
    }

    public static Country create(String value) {
        return new Country(validateCountry(value));
    }

    private static String validateCountry(String value) {
        String optionalValue = Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .orElseThrow(InvalidCountryException::thrown)
                .trim()
                .replaceAll(REGEX_SPECIAL_CHARS, EMPTY_STRING);

        if (optionalValue.isEmpty()) {
            throw InvalidCountryException.thrown();
        }

        return CountryType
                .valueOf(optionalValue.toUpperCase())
                .name();
    }

    public String getValue() {
        return value;
    }
}
