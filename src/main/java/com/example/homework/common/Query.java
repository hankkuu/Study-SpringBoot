package com.example.homework.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Query {
    private String sortField;
    private int sortMethod = 0;
}
