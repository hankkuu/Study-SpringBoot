package com.example.homework.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse extends Response {
    // HttpStatus
    private String status;
    // Http Default Message
    private String message;

    public ApiResponse() {
        super();
    }

    public ApiResponse(String status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
}
