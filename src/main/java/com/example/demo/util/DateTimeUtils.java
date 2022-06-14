package com.example.demo.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTimeUtils {
    public Date convertStringToDate(String dateTimeString){
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis();
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeString);
        return dateTime.toDate();
    }
    public ZoneId convertStringToZoneDate(String dateTimeString){
        ZonedDateTime parse = ZonedDateTime.parse(dateTimeString);
        ZoneId zone = parse.getZone();
        return zone;
    }

    public String convertStringFromDateTime(Date date, ZoneId zoneId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdf.setTimeZone(TimeZone.getTimeZone(zoneId));
        String dateString = sdf.format(date);
        return dateString;

    }

    public Date roundToHour(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if(calendar.get(Calendar.MINUTE) > 0 || calendar.get(Calendar.SECOND) > 0){
            calendar.add(Calendar.HOUR, 1);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        }
        return calendar.getTime();
    }




}
