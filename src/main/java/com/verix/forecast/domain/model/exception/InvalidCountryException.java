package com.verix.forecast.domain.model.exception;

public class InvalidCountryException extends RuntimeException {
    private static final String MESSAGE = "Try to set null, empty or invalid Country";

    private InvalidCountryException(String message) {
        super(message);
    }

    public static InvalidCountryException thrown() {
        return new InvalidCountryException(MESSAGE);
    }
}
