package com.verix.sam.domain.model.exception;

public class SamWriterException extends RuntimeException {
    private static final String MESSAGE = "Data could not be saved to the DataWarehouse";

    private SamWriterException(String message) {
        super(message);
    }

    public static SamWriterException thrown() {
        return new SamWriterException(MESSAGE);
    }
}
