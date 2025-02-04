package com.verix.forecast.domain.model.exception;

public class InvalidDateException extends RuntimeException {
    private static final String MESSAGE = "Try to set null or empty value for Delivery Date";

    private InvalidDateException(String message) {
        super(message);
    }

    public static InvalidDateException thrown() {
        return new InvalidDateException(MESSAGE);
    }
}
