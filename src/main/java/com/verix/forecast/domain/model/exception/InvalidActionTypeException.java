package com.verix.forecast.domain.model.exception;

public class InvalidActionTypeException extends RuntimeException {
    private static final String MESSAGE = "Try to set null or empty value for Action Type";

    private InvalidActionTypeException(String message) {
        super(message);
    }

    public static InvalidActionTypeException thrown() {
        return new InvalidActionTypeException(MESSAGE);
    }
}
