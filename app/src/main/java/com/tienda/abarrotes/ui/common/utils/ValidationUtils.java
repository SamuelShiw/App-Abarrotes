package com.tienda.abarrotes.ui.common.utils;

public final class ValidationUtils {

    private ValidationUtils() {
        // Evita instanciación
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isOnlyDigits(String value) {
        return value != null && value.matches("\\d+");
    }

    public static boolean isValidDni(String dni) {
        return dni != null && dni.matches("\\d{8}");
    }

    public static boolean isValidPhone(String telefono) {
        return telefono != null && telefono.matches("\\d{9}");
    }
}