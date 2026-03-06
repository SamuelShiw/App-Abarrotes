package com.tienda.abarrotes.ui.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private DateUtils() {
    }

    public static String now() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
    }

    public static String today() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }
}