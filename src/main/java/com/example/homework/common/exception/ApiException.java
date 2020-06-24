package com.example.homework.common.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiException extends Exception {
    // Error Code
    private int errorCode;
    // Error Message to USER
    private String errorMessage;

    public ApiException(String message, int errorCode, String errorMessage) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
