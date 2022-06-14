package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponseDto<T> implements Serializable {
    private static final long serialVersionUID = -7249248962932628189L;

    private int returnCode;
    private String returnMessage;

    private T data;

    public BaseResponseDto success(String message){
        this.returnCode = 1;
        this.returnMessage = message;
        return this;
    }

    public BaseResponseDto fail(int returnCode, String message){
        this.returnCode = returnCode;
        this.returnMessage = message;
        return this;
    }
}
