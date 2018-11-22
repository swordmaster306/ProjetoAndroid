package com.example.alexy.redesocial.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Formatter {
    public static String date(String date, String pattern) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat(pattern);
        Date d = dateFormat.parse(date);
        return output.format(d);
    }
    public static String date(String date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date d = dateFormat.parse(date);
        return output.format(d);
    }
}
