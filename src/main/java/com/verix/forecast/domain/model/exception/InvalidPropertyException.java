package com.verix.forecast.domain.model.exception;

public class InvalidPropertyException extends RuntimeException {
    private static final String MESSAGE = "Try to set null or empty value";

    private InvalidPropertyException(String message) {
        super(message);
    }

    public static InvalidPropertyException thrown() {
        return new InvalidPropertyException(MESSAGE);
    }
}
