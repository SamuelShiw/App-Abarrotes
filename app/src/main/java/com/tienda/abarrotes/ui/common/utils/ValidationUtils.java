package com.tienda.abarrotes.ui.common.utils;

public final class ValidationUtils {

    private ValidationUtils() {
        // Evita instanciación
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}