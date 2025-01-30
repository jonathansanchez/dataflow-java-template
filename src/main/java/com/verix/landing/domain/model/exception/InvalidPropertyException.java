package com.verix.landing.domain.model.exception;

public class InvalidPropertyException extends RuntimeException {
    private static final String MESSAGE = "Trying to set null or empty value for a required property.";

    private InvalidPropertyException(String message) {
        super(message);
    }

    public static InvalidPropertyException thrown(){
        return new InvalidPropertyException(MESSAGE);
    }
}
