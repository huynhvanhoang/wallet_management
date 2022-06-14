package com.example.demo.repository;

import com.example.demo.model.AccountBalance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AccountBalanceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountBalanceRepository accountBalanceRepository;

    @Test
    @DisplayName("Should returns all account balances when the start date and end date are valid")
    public void
    testFindAllByRangeTimeWhenStartDateAndEndDateAreValidThenReturnsAllAccountBalances() {
        AccountBalance accountBalance1 = new AccountBalance();
        accountBalance1.setCurrent_balance(100);
        accountBalance1.setAmount(100);
        accountBalance1.setCreatedAt(new Date(1577836800000L));

        AccountBalance accountBalance2 = new AccountBalance();
        accountBalance2.setCurrent_balance(200);
        accountBalance2.setAmount(200);
        accountBalance2.setCreatedAt(new Date(1577923200000L));

        entityManager.persist(accountBalance1);
        entityManager.persist(accountBalance2);

        List<AccountBalance> actual =
                accountBalanceRepository.findAllByRangeTime(1577836800000L, 1577923200000L);

        assertEquals(2, actual.size());
    }

    @Test
    @DisplayName("Should returns empty list when the start date and end date are invalid")
    public void testFindAllByRangeTimeWhenStartDateAndEndDateAreInvalidThenReturnsEmptyList() {
        long startDate = 0;
        long endDate = 0;

        List<AccountBalance> accountBalances =
                accountBalanceRepository.findAllByRangeTime(startDate, endDate);

        assertEquals(0, accountBalances.size());
    }

    @Test
    @DisplayName("Should returns the latest account balance")
    public void testFindTopByOrderByIdDescShouldReturnsTheLatestAccountBalance() {
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setCurrent_balance(100);
        accountBalance.setAmount(100);
        accountBalance.setCreatedAt(new Date());
        accountBalanceRepository.save(accountBalance);

        AccountBalance result = accountBalanceRepository.findTopByOrderByIdDesc();

        assertEquals(accountBalance, result);
    }
}