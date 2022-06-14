package com.example.demo.service;

import com.example.demo.dto.AccountBalanceDto;
import com.example.demo.model.AccountBalance;
import com.example.demo.repository.AccountBalanceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountBalanceServiceImplTestbk {

    @Autowired
    AccountBalanceService accountBalanceService;

    @MockBean
    AccountBalanceRepository accountBalanceRepository;

    @Test
    @DisplayName("Should return list of account balance when the start date and end date are valid")
    public void
    testGetBalanceHistoryWhenStartDateAndEndDateAreValidThenReturnListOfAccountBalance() {
        String startDatetime = "2020-01-01T00:00:00+07:00";
        String endDatetime = "2020-01-02T00:00:00+07:00";
        List<AccountBalance> balanceList = new ArrayList<>();
        AccountBalance accountBalance1 = new AccountBalance();
        accountBalance1.setAmount(10);
        accountBalance1.setCreated_at(new Date(1577811600000L).getTime());
        AccountBalance accountBalance2 = new AccountBalance();
        accountBalance2.setAmount(20);
        accountBalance2.setCreated_at(new Date(1577898000000L).getTime());
        balanceList.add(accountBalance1);
        balanceList.add(accountBalance2);

        when(accountBalanceRepository.findAllByRangeTime(1577811600000L, 1577898000000L))
                .thenReturn(balanceList);

        List<AccountBalanceDto> balanceHistory =
                accountBalanceService.getBalanceHistory(startDatetime, endDatetime);

        assertEquals(2, balanceHistory.size());
    }

    @Test
    @DisplayName("Should return empty list when the start date and end date are invalid")
    public void testGetBalanceHistoryWhenStartDateAndEndDateAreInvalidThenReturnEmptyList() {
        String startDatetime = "2020-01-01T00:00:00+07:00";
        String endDatetime = "2020-01-01T00:00:00+07:00";
        List<AccountBalance> balanceList = new ArrayList<>();
        when(accountBalanceRepository.findAllByRangeTime(anyLong(), anyLong()))
                .thenReturn(balanceList);

        List<AccountBalanceDto> balanceHistory =
                accountBalanceService.getBalanceHistory(startDatetime, endDatetime);

        assertEquals(0, balanceHistory.size());
    }
}