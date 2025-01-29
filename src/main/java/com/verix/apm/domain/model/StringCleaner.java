package com.verix.apm.domain.model;

import com.verix.apm.domain.model.exception.InvalidPropertyException;

import java.util.Optional;
import java.util.function.Predicate;

public class StringCleaner {
    private static final String REGEX_SPECIAL_CHARS = "[^a-zA-Z0-9\\s.,-@#$/]";
    private static final String REPLACEMENT = "";

    public static String removeSpecialCharsForOptional(String value) {
        return Optional
                .ofNullable(value)
                .filter(Predicate.not(String::isEmpty)) // Filtra si la cadena no está vacía
                .map(s -> s.trim().replaceAll(REGEX_SPECIAL_CHARS, REPLACEMENT))  // Elimina caracteres no permitidos
                .orElse(REPLACEMENT);  // Si es null/vacío, devuelve una cadena vacía
    }

    public static String removeSpecialCharsForRequired(String value) {
        return Optional
                .ofNullable(value)
                .orElseThrow(InvalidPropertyException::thrown)  // Lanza excepción si es nulo
                .trim()
                .replaceAll(REGEX_SPECIAL_CHARS, REPLACEMENT);
    }
}
