package com.example.homework.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiException extends Exception {
    // Error Code
    private String errorCode;
    // Error Message to USER
    private String errorMessage;

    public ApiException(String message, String errorCode, String errorMessage) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
