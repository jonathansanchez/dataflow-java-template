package com.verix.apm.domain.model.exception;

public class ApmNotFoundException extends RuntimeException {
    private static final String MESSAGE = "SAM not found in DataLake";

    private ApmNotFoundException(String message) {
        super(message);
    }

    public static ApmNotFoundException thrown() {
        return new ApmNotFoundException(MESSAGE);
    }
}
