package com.example.demo.controller;

import com.example.demo.dto.AccountBalanceDto;
import com.example.demo.dto.BaseResponseDto;
import com.example.demo.service.AccountBalanceService;
import com.example.demo.util.ValidateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountBalanceController.class)
public class AccountBalanceControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ValidateUtils validateUtils;
    @MockBean
    AccountBalanceService balanceService;
    @Autowired
    AccountBalanceController accountBalanceController;

    @Test
    @DisplayName("Should returns 400 when the request parameter is invalid")
    public void testAddCoinWhenRequestParameterIsInvalidThenReturns400() {
        when(validateUtils.isValidDate(anyString())).thenReturn(false);

        AccountBalanceDto accountBalanceDto =
                new AccountBalanceDto(1d, "2019-01-01T00:00:00+07:00");
        try {
            mockMvc.perform(
                            post("/api/v1/addCoin")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(
                                            new ObjectMapper()
                                                    .writeValueAsString(accountBalanceDto)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should returns 200 when the request parameter is valid")
    public void testAddCoinWhenRequestParameterIsValidThenReturns200() {
        AccountBalanceDto accountBalanceDto =
                new AccountBalanceDto(100d, "2020-01-01T00:00:00+07:00");
        when(validateUtils.isValidDate(accountBalanceDto.getDatetime())).thenReturn(true);
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        baseResponseDto.success("success");
        when(balanceService.addCoin(accountBalanceDto)).thenReturn(baseResponseDto);

        try {
            mockMvc.perform(
                            post("/api/v1/addCoin")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(
                                            new ObjectMapper()
                                                    .writeValueAsString(accountBalanceDto)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should returns bad request when startDatetime is invalid")
    public void testGetAccountBalanceHistoryWhenStartDatetimeIsInvalidThenReturnsBadRequest() {
        when(validateUtils.isValidDate("2020-01-01T00:00:00+07:00")).thenReturn(false);
        when(validateUtils.isValidDate("2020-01-02T00:00:00+07:00")).thenReturn(true);

        try {
            mockMvc.perform(
                            get("/api/v1/getAccountBalanceHistory")
                                    .param("startDatetime", "2020-01-01T00:00:00+07:00")
                                    .param("endDatetime", "2020-01-02T00:00:00+07:00"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should returns bad request when endDatetime is invalid")
    public void testGetAccountBalanceHistoryWhenEndDatetimeIsInvalidThenReturnsBadRequest() {
        when(validateUtils.isValidDate("2020-01-01T00:00:00+07:00")).thenReturn(true);
        when(validateUtils.isValidDate("2020-01-01T00:00:00+07:00")).thenReturn(false);

        try {
            mockMvc.perform(
                            get("/api/v1/getAccountBalanceHistory")
                                    .param("startDatetime", "2020-01-01T00:00:00+07:00")
                                    .param("endDatetime", "2020-01-01T00:00:00+07:00"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}