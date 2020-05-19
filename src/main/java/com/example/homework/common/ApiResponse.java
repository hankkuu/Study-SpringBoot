package com.example.homework.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse extends Response {

    public ApiResponse() {
        super();
    }

    public ApiResponse(String status, String message) {
        super(status, message);
    }
}
