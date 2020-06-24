package com.example.homework.common;

import com.example.homework.common.util.ResponseUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Response<T> implements ResponseUtil {

    @Override
    public <T> void excludeHtmlTag(List<T> list) throws IllegalAccessException {

        for(T item : list) {
            Field[] fileds = item.getClass().getDeclaredFields();
            for(Field f : fileds) {
                f.setAccessible(true);
                Object property = f.get(item);
                if(property instanceof String) {
                    String s = property.toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                    f.set(item, s);
                }
            }
        }
    }
}
