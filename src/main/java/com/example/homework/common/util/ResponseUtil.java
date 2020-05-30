package com.example.homework.common.util;

import java.util.List;

public interface ResponseUtil {

    <T> void excludeHtmlTag(List<T> item) throws IllegalAccessException;
}
