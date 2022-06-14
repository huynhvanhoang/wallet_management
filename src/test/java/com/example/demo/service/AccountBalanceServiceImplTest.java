package com.example.demo.service;

import com.example.demo.config.CacheClient;
import com.example.demo.dto.AccountBalanceDto;
import com.example.demo.dto.BaseResponseDto;
import com.example.demo.model.AccountBalance;
import com.example.demo.repository.AccountBalanceRepository;
import com.example.demo.util.DateTimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountBalanceServiceImplTest {

    @MockBean
    AccountBalanceRepository accountBalanceRepository;
    @MockBean
    CacheClient cacheClient;
    @MockBean
    DateTimeUtils dateTimeUtils;

    @Autowired
    AccountBalanceService accountBalanceService;

    @Test
    @DisplayName("Should saves the account balance when the amount and datetime is not null")
    public void testAddCoinWhenAmountAndDateTimeIsNotNull() {
        AccountBalanceDto accountBalanceDto = new AccountBalanceDto(1d, "2020-01-01T00:00:00+00:00");
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setId(1L);
        accountBalance.setAmount(1);
        accountBalance.setCreated_at(1577836800000L);
        when(accountBalanceRepository.findTopByOrderByIdDesc()).thenReturn(null);
        when(accountBalanceRepository.save(any())).thenReturn(accountBalance);
        when(dateTimeUtils.convertStringToDate(any())).thenReturn(new Date());
        BaseResponseDto responseDto = accountBalanceService.addCoin(accountBalanceDto);
        assertEquals(1, responseDto.getReturnCode());
        assertEquals("The coin is added successfully", responseDto.getReturnMessage());
    }


    @Test
    @DisplayName(
            "Should return list of AccountBalanceDto when the startDatetime and endDatetime are valid")
    public void
    testGetBalanceHistoryWhenStartDatetimeAndEndDatetimeAreValidThenReturnListOfAccountBalanceDto() {
        String startDatetime = "2020-01-01T00:00:00+07:00";
        String endDatetime = "2020-01-01T23:59:59+07:00";
        List<AccountBalance> accountBalances = new ArrayList<>();
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAmount(100);
        accountBalance.setCurrent_balance(100);
        accountBalance.setCreated_at(new Date().getTime());
        accountBalances.add(accountBalance);

        when(dateTimeUtils.convertStringToDate(startDatetime)).thenReturn(new Date());
        when(dateTimeUtils.convertStringToDate(endDatetime)).thenReturn(new Date());
        when(dateTimeUtils.convertStringToZoneDate(startDatetime)).thenReturn(ZoneId.of("+07:00"));
        when(dateTimeUtils.roundToHour(new Date(accountBalance.getCreated_at()))).thenReturn(new Date(1577811600000L));
        when(dateTimeUtils.convertStringFromDateTime(new Date(1577811600000L), ZoneId.of("+07:00"))).thenReturn("2020-01-01T00:00:00+07:00");
        when(cacheClient.getListAccountBalanceCache(anyLong(), anyLong()))
                .thenReturn(accountBalances);

        List<AccountBalanceDto> balanceHistory =
                accountBalanceService.getBalanceHistory(startDatetime, endDatetime);

        assertEquals(1, balanceHistory.size());
    }

    @Test
    @DisplayName("Should return empty list when the startDatetime and endDatetime are invalid")
    public void
    testGetBalanceHistoryWhenStartDatetimeAndEndDatetimeAreInvalidThenReturnEmptyList() {
        String startDatetime = "2020-01-01T00:00:00+07:00";
        String endDatetime = "2020-01-01T00:00:00+07:00";
        List<AccountBalance> balanceList = new ArrayList<>();
        when(dateTimeUtils.convertStringToDate(startDatetime)).thenReturn(new Date());
        when(dateTimeUtils.convertStringToDate(endDatetime)).thenReturn(new Date());
        when(dateTimeUtils.convertStringToZoneDate(startDatetime)).thenReturn(null);
        when(cacheClient.getListAccountBalanceCache(anyLong(), anyLong())).thenReturn(balanceList);
        when(accountBalanceRepository.findAllByRangeTime(anyLong(), anyLong()))
                .thenReturn(balanceList);

        List<AccountBalanceDto> balanceHistory =
                accountBalanceService.getBalanceHistory(startDatetime, endDatetime);

        assertEquals(0, balanceHistory.size());
    }
}