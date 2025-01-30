package com.verix.apm.domain.model;

public class BooleanCleaner {
    public static Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return "YES".equalsIgnoreCase(value); //ignora mayus o minuscula
    }
}
