package com.example.homework.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response {
    // HttpStatus
    private String status;
    // Http Default Message
    private String message;
    Response() { }
    Response(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
