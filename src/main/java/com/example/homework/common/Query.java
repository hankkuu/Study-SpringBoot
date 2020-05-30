package com.example.homework.common;

import com.example.homework.common.exception.ApiException;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.List;

@Getter
@Setter
public class Query {
    private String sortField = "";
    private int sortMethod = 0;

    public void sortResponseEntity(List list) throws ApiException {
        // sorting 로직 추가 리플렉션 사용 / 람다로 comparator 구현
        try {
            String field = new String("get").concat(this.sortField.toLowerCase());

            list.sort((movie1, movie2) -> {
                Object o1 = null;
                Object o2 = null;

                Class<?> movie = movie1.getClass();
                Method[] methods = movie.getMethods();
                for (Method method : methods) {
                    if (method.getName().toLowerCase().equals(field)) {
                        try {
                            o1 = method.invoke(movie1);
                            o2 = method.invoke(movie2);
                            // 람다식 내부에서는 런타임예외를 뱉어야 하는 것 같다
                        } catch (Exception e) {
                            throw new RuntimeException();
                        }
                    }
                }
                // 0 이면 오름차순 정렬 1이면 내림차순 정렬 default 는 오름차순으로 한다
                if (this.sortMethod == 1) {
                    return o2.toString().compareTo(o1.toString());
                } else {
                    return o1.toString().compareTo(o2.toString());
                }

            });

        } catch (Exception e) {
            throw new ApiException("fail to sort from user query", 10001, "sorting Error!!!");
        }
    }
}
