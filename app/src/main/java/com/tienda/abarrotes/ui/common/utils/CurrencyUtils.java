package com.tienda.abarrotes.ui.common.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {

    private CurrencyUtils() {
    }

    public static String formatSoles(double amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("es", "PE"));
        return numberFormat.format(amount);
    }
}