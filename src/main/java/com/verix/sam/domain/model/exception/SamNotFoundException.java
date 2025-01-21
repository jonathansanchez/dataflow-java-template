package com.verix.sam.domain.model.exception;

public class SamNotFoundException extends RuntimeException {
    private static final String MESSAGE = "SAM not found in DataLake";

    private SamNotFoundException(String message) {
        super(message);
    }

    public static SamNotFoundException thrown() {
        return new SamNotFoundException(MESSAGE);
    }
}
