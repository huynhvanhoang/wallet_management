package com.example.demo.config;

import com.example.demo.util.DateTimeUtils;
import com.example.demo.util.ValidateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("Should return a DateTimeUtils object")
public class BeanConfigTest {

    @Autowired
    private BeanConfig beanConfig;

    @Test
    @DisplayName("Should return a ValidateUtils object")
    public void testValidateUtilsShouldReturnValidateUtilsObject() {
        assertTrue(beanConfig.validateUtils() instanceof ValidateUtils);
    }

    @Test
    @DisplayName("Should return a DateTimeUtils object")
    public void testDateTimeUtilsShouldReturnDateTimeUtilsObject() {
        assertTrue(beanConfig.dateTimeUtils() instanceof DateTimeUtils);
    }
}