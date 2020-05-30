package com.example.homework.common.util;

import com.example.homework.common.exception.ApiException;
import com.example.homework.common.QueryStringAnnotation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Convertor {

    public static Map<String, Object> converObjectToMap(Object o) throws ApiException {
        Map<String, Object> map = new HashMap<>();
        try {
            Field[] fields = o.getClass().getDeclaredFields();

            for(Field field : fields) {
                field.setAccessible(true);  // private 접근을 가능하게
                if(field.get(o) != null) {
                    QueryStringAnnotation q = field.getAnnotation(QueryStringAnnotation.class);
                    if(q == null)
                        map.put(field.getName(), field.get(o));
                    else {
                        if(q.isIgnore() == false)
                            map.put(field.getName(), field.get(o));
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            throw new ApiException(e.getMessage(), 10002, "");
        } catch (IllegalAccessException e) {
            throw new ApiException(e.getMessage(), 10002, "");
        }

        return map;
    }
}
