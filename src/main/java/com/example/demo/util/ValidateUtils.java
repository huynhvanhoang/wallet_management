package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidateUtils {
    public boolean isValidDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
