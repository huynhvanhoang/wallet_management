package com.example.demo.service;

import com.example.demo.config.CacheClient;
import com.example.demo.dto.AccountBalanceDto;
import com.example.demo.dto.BaseResponseDto;
import com.example.demo.model.AccountBalance;
import com.example.demo.repository.AccountBalanceRepository;
import com.example.demo.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {

    @Autowired
    DateTimeUtils dateTimeUtils;
    @Autowired
    AccountBalanceRepository accountBalanceRepository;

    @Autowired
    CacheClient cacheClient;

    @Override
    @Transactional
    public BaseResponseDto addCoin(AccountBalanceDto accountBalanceDto) {
        AccountBalance lastAccountBalance = accountBalanceRepository.findTopByOrderByIdDesc();
        AccountBalance accountBalance = convertAccountBalanceToEntity(accountBalanceDto, lastAccountBalance);

        AccountBalance balance = accountBalanceRepository.save(accountBalance);
        cacheClient.putAccountBalanceCache(balance);
        if (Objects.nonNull(balance.getId()))
            return new BaseResponseDto<>().success("The coin is added successfully");
        else
            return new BaseResponseDto<>().fail(0, "The coin is added fail");
    }

    @Override
    public List<AccountBalanceDto> getBalanceHistory(String startDatetime, String endDatetime) {
        long startDate = dateTimeUtils.convertStringToDate(startDatetime).getTime();
        long endDate = dateTimeUtils.convertStringToDate(endDatetime).getTime();
        ZoneId zoneId = dateTimeUtils.convertStringToZoneDate(startDatetime);
        List<AccountBalance> balanceList;
        balanceList = cacheClient.getListAccountBalanceCache(startDate, endDate);
        if(balanceList.size() == 0)
            balanceList = accountBalanceRepository.findAllByRangeTime(startDate, endDate);
        List<AccountBalanceDto> collect = convertAccountBalanceListToDto(zoneId, balanceList);

        return collect;
    }

    private List<AccountBalanceDto> convertAccountBalanceListToDto(ZoneId zoneId, List<AccountBalance> balanceList) {
        List<AccountBalanceDto> balanceDtoList = new ArrayList<>();
        for (AccountBalance accountBalance : balanceList) {
            AccountBalanceDto dto = new AccountBalanceDto();
            dto.setAmount(accountBalance.getAmount());
            Date date = dateTimeUtils.roundToHour(new Date(accountBalance.getCreated_at()));
            String dateTime = dateTimeUtils.convertStringFromDateTime(date, zoneId);
            dto.setDatetime(dateTime);
            balanceDtoList.add(dto);
        }
        balanceDtoList.sort(comparing(AccountBalanceDto::getDatetime, Comparator.reverseOrder())
                .thenComparing(AccountBalanceDto::getAmount, Comparator.reverseOrder()));

        List<AccountBalanceDto> collect = balanceDtoList.stream().collect(
                collectingAndThen(toCollection(() ->
                        new TreeSet<>(comparing(AccountBalanceDto::getDatetime))), ArrayList::new));
        return collect;
    }

    private AccountBalance convertAccountBalanceToEntity(AccountBalanceDto accountBalanceDto, AccountBalance lastAccountBalance) {
        AccountBalance accountBalance = new AccountBalance();
        double amount = accountBalanceDto.getAmount();
        if (Objects.nonNull(lastAccountBalance))
            amount += lastAccountBalance.getAmount();
        accountBalance.setCurrent_balance(amount);
        accountBalance.setAmount(amount);
        accountBalance.setCreatedAt(dateTimeUtils.convertStringToDate(accountBalanceDto.getDatetime()));
        return accountBalance;
    }
}
