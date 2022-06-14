package com.example.demo.dto;

import com.example.demo.util.DateTimeUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Data
public class AccountBalanceDto implements Serializable {
    private static final long serialVersionUID = -7249248962932628189L;
    private Double amount;
    private String datetime;

    public AccountBalanceDto() {
    }

    public AccountBalanceDto(Double s, String s1) {
        this.amount = s;
        this.datetime=  s1;
    }

}
