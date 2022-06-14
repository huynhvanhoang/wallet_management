package com.example.demo.service;

import com.example.demo.dto.AccountBalanceDto;
import com.example.demo.dto.BaseResponseDto;

import java.util.List;

public interface AccountBalanceService {

    BaseResponseDto addCoin(AccountBalanceDto accountBalanceDto);


    List<AccountBalanceDto> getBalanceHistory(String startDatetime, String endDatetime);

}
