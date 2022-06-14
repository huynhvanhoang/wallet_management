package com.example.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("AccountBalance")
public class AccountBalanceTest {

    @Test
    @DisplayName("Should sets the created_at field to the current time")
    public void testSetCreatedAtWhenDateIsNotNullThenSetsTheCreatedAtFieldToTheCurrentTime() {
        AccountBalance accountBalance = new AccountBalance();
        Date date = new Date();

        accountBalance.setCreatedAt(date);

        assertEquals(date.getTime(), accountBalance.getCreated_at());
    }
}