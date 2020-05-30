package com.example.homework.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        int errorCode = ex.getErrorCode();
        switch(errorCode/10000) {
            case 1: {
                return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            case 2: {
                return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
            }
            default:
                return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
