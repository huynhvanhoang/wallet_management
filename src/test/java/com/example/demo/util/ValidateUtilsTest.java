package com.example.demo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateUtilsTest {

    private ValidateUtils validateUtils;

    @BeforeEach
    public void setUp() {
        validateUtils = new ValidateUtils();
    }

    @Test
    @DisplayName("Should return true when the dateStr is valid")
    public void testIsValidDateWhenDateStrIsValid() {
        String dateStr = "2019-01-01T00:00:00+00:00";
        assertTrue(validateUtils.isValidDate(dateStr));
    }

    @Test
    @DisplayName("Should return false when the dateStr is invalid")
    public void testIsValidDateWhenDateStrIsInvalid() {
        String dateStr = "2019-02-29T12:00:00+08:00";
        boolean result = validateUtils.isValidDate(dateStr);
        assertFalse(result);
    }

}