package com.example.demo.controller;

import com.example.demo.dto.AccountBalanceDto;
import com.example.demo.dto.BaseResponseDto;
import com.example.demo.service.AccountBalanceService;
import com.example.demo.util.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller()
public class AccountBalanceController {
    @Autowired
    ValidateUtils validateUtils;
    @Autowired
    AccountBalanceService balanceService;

    @PostMapping("/api/v1/addCoin")
    public ResponseEntity<?> addCoin(@RequestBody AccountBalanceDto accountBalanceDto){
        if(Objects.isNull(accountBalanceDto) || !validateUtils.isValidDate(accountBalanceDto.getDatetime()))
            return ResponseEntity.badRequest().body("request parameter is invalid");
        BaseResponseDto baseResponseDto = balanceService.addCoin(accountBalanceDto);
        return ResponseEntity.ok(baseResponseDto);
    }

    @GetMapping("/api/v1/getAccountBalanceHistory")
    public ResponseEntity<?> getAccountBalanceHistory(@RequestParam String startDatetime, @RequestParam String endDatetime){
        if(!validateUtils.isValidDate(startDatetime) || !validateUtils.isValidDate(endDatetime))
            return ResponseEntity.badRequest().body("request parameter is invalid");
        List<AccountBalanceDto> list = balanceService.getBalanceHistory(startDatetime, endDatetime);
        return ResponseEntity.ok(list);
    }
}