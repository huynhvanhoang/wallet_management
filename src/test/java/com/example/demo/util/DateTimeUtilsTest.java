package com.example.demo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeUtilsTest {
    private DateTimeUtils dateTimeUtils;

    @BeforeEach
    public void setUp() {
        dateTimeUtils = new DateTimeUtils();
    }

    @Test
    @DisplayName("Should return the zone when the dateTimeString is valid")
    public void testConvertStringToZoneDateWhenDateTimeStringIsValidThenReturnTheZone() {
        String dateTimeString = "2022-01-01T00:00:00+01:00";
        ZoneId expectedZone = ZoneId.of("+01:00");

        ZoneId actualZone = dateTimeUtils.convertStringToZoneDate(dateTimeString);

        assertEquals(expectedZone, actualZone);
    }

    @Test
    @DisplayName("Should throws an exception when the dateTimeString is invalid")
    public void testConvertStringToZoneDateWhenDateTimeStringIsInvalidThenThrowsException() {
        String dateTimeString = "2022-01-01T00:00:00";
        assertThrows(
                DateTimeParseException.class,
                () -> dateTimeUtils.convertStringToZoneDate(dateTimeString));
    }

    @Test
    @DisplayName("Should return the date when the dateTimeString is valid")
    public void testConvertStringToDateWhenDateTimeStringIsValidThenReturnTheDate() {
        String dateTimeString = "2022-01-01T00:00:00+07:00";
        Date expectedDate = new Date(1640970000000L);

        Date actualDate = dateTimeUtils.convertStringToDate(dateTimeString);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    @DisplayName("Should throws an exception when the dateTimeString is invalid")
    public void testConvertStringToDateWhenDateTimeStringIsInvalidThenThrowsException() {
        String dateTimeString = "2022-02-29T10:15:30+01:00";
        assertThrows(
                IllegalArgumentException.class,
                () -> dateTimeUtils.convertStringToDate(dateTimeString));
    }

    @Test
    @DisplayName("Should return the same date when the date is already rounded")
    public void testRoundToHourWhenDateIsAlreadyRoundedThenReturnTheSameDate() {
        Date date = new GregorianCalendar(2022, Calendar.JANUARY, 1, 0, 0).getTime();

        Date roundedDate = dateTimeUtils.roundToHour(date);

        assertEquals(date, roundedDate);
    }

    @Test
    @DisplayName("Should return the next hour when the date is not rounded")
    public void testRoundToHourWhenDateIsNotRoundedThenReturnTheNextHour() {
        Date date = new GregorianCalendar(2022, Calendar.JANUARY, 1, 12, 30).getTime();
        Date expectedDate = new GregorianCalendar(2022, Calendar.JANUARY, 1, 13, 0).getTime();

        Date actualDate = dateTimeUtils.roundToHour(date);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    @DisplayName("Should return zoneId when the dateTimeString is valid")
    public void testConvertStringToZoneDateWhenDateTimeStringIsValidThenReturnZoneId() {
        String dateTimeString = "2022-12-31T23:59:59+01:00";
        ZoneId expectedZoneId = ZoneId.of("+01:00");

        ZoneId actualZoneId = dateTimeUtils.convertStringToZoneDate(dateTimeString);

        assertEquals(expectedZoneId, actualZoneId);
    }

    @Test
    @DisplayName("Should return date when the dateTimeString is valid")
    public void testConvertStringToDateWhenDateTimeStringIsValidThenReturnDate() {
        String dateTimeString = "2022-12-31T23:59:59+01:00";
        Date date = dateTimeUtils.convertStringToDate(dateTimeString);
        assertNotNull(date);
    }

    @Test
    @DisplayName("Should return the correct date time string when the date is not null")
    public void testConvertStringFromDateTimeWhenDateIsNotNullThenReturnCorrectDateTimeString() {
        Date date = new Date(1655018037434L);
        ZoneId zoneId = ZoneId.of("+07:00");

        String dateTimeString = dateTimeUtils.convertStringFromDateTime(date, zoneId);
        System.out.println(dateTimeString);
        assertNotNull(dateTimeString);
    }

    @Test
    @DisplayName("Should return the correct date time string when the zone id is not null")
    public void testConvertStringFromDateTimeWhenZoneIdIsNotNullThenReturnCorrectDateTimeString() {
        Date date = new Date();
        ZoneId zoneId = ZoneId.of("Europe/Paris");

        String dateTimeString = dateTimeUtils.convertStringFromDateTime(date, zoneId);

        assertNotNull(dateTimeString);
    }
}