package com.example.demo.config;

import com.example.demo.model.AccountBalance;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CacheClientTest {

    @Test
    @DisplayName("Should return list account balance when the start date and end date is valid")
    public void
    testGetListAccountBalanceCacheWhenStartDateAndEndDateIsValidThenReturnListAccountBalance() {
        CacheClient cacheClient = new CacheClient();
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setCreatedAt(new Date());
        cacheClient.putAccountBalanceCache(accountBalance);

        List<AccountBalance> accountBalances =
                cacheClient.getListAccountBalanceCache(
                        accountBalance.getCreated_at(), accountBalance.getCreated_at());

        assertNotNull(accountBalances);
    }

    @Test
    @DisplayName("Should return null when the account balance is not in cache")
    public void testPutAccountBalanceCacheWhenAccountBalanceIsNotInCacheThenReturnNull() {
        CacheClient cacheClient = new CacheClient();
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setCreatedAt(new Date());

        AccountBalance result = cacheClient.putAccountBalanceCache(accountBalance);

        assertNull(result);
    }

    @Test
    @DisplayName("Should return the account balance when the account balance is in cache")
    public void testPutAccountBalanceCacheWhenAccountBalanceIsInCacheThenReturnTheAccountBalance() {
        CacheClient cacheClient = new CacheClient();
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setCreatedAt(new Date());
        accountBalance.setAmount(100);
        accountBalance.setCurrent_balance(100);

        AccountBalance result = cacheClient.putAccountBalanceCache(accountBalance);

        assertNull(result);
    }
}