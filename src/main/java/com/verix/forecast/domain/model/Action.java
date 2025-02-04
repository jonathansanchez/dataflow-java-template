package com.verix.forecast.domain.model;

import com.verix.forecast.domain.model.exception.InvalidActionTypeException;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Predicate;

public class Action implements Serializable {
    private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z]";
    private static final String EMPTY_STRING = "";

    private final String value;

    private Action(String value) {
        this.value = value;
    }

    public static Action create(String value) {
        return new Action(validateAction(value));
    }

    private static String validateAction(String value) {
        String optionalValue = Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty))
                .orElseThrow(InvalidActionTypeException::thrown)
                .trim()
                .replaceAll(REGEX_SPECIAL_CHARS, EMPTY_STRING);

        if (optionalValue.isEmpty()) {
            throw InvalidActionTypeException.thrown();
        }

        return ActionType
                .valueOf(optionalValue.toUpperCase())
                .name();
    }

    public String getValue() {
        return value;
    }
}
