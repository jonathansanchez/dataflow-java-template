package com.verix.sam.domain.model.exception;

public class InvalidPropertyException extends RuntimeException {
    private static final String MESSAGE = "Try to set null value";

    private InvalidPropertyException(String message) {
        super(message);
    }

    public static InvalidPropertyException thrown() {
        return new InvalidPropertyException(MESSAGE);
    }
}
