package com.example.senocare.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class TimeConverter {

    public static Date toDate(String date, String format) {
        try {
            return new SimpleDateFormat(format, Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(Date date, String format) {
        return new SimpleDateFormat(format, Locale.US).format(date);
    }
}
