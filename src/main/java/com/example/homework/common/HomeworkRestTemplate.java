package com.example.homework.common;

import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class HomeworkRestTemplate extends RestTemplate {

    @SneakyThrows
    public <T> ResponseEntity<T> exchangeExcludeHtmlTag(String url, HttpMethod method,
                                          @Nullable HttpEntity<?> requestEntity, Class<T> responseType,  Class<?> convertType, Object... uriVariables)
            throws RestClientException {

        ResponseEntity<T> result = super.exchange(url,method,  requestEntity, responseType, uriVariables);

        T body = result.getBody();
        Field[] fields = body.getClass().getDeclaredFields();
        for(Field f1 : fields) {
            f1.setAccessible(true);
            Object obj = f1.get(body);
            if(obj instanceof List) {
                for(Object o : (List)obj) {
                    Object c =  convertType.cast(o);
                    Field[] fields2 = c.getClass().getDeclaredFields();
                    for(Field f2 : fields2) {
                        f2.setAccessible(true);
                        Object property = f2.get(c);
                        if(property instanceof String) {
                            String s = property.toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                            f2.set(c, s);
                        }
                    }
                }
            }
        }

        return result;
    }
}
