package com.example.homework.service;

import com.example.homework.common.ApiException;
import com.example.homework.config.AppConfigManager;
import com.example.homework.model.request.BookRequest;
import com.example.homework.model.request.MovieRequest;
import com.example.homework.model.response.BookResponse;
import com.example.homework.model.response.MovieResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.DocFlavor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

@Slf4j
@Service
public class NaverService {

    // value 방식에서는 생성자에서 설정을 바로 가져오지 못한다 Bean 생명주기상 나중에 만들어지는 것 같다
    //    @Value("${app.api.naver.clientId}")
    private String clientId; //애플리케이션 클라이언트 아이디값"
    //    @Value("${app.api.naver.clientSecret}")
    private String clientSecret; //애플리케이션 클라이언트 시크릿값"
    //    @Value("${app.api.naver.search.book}")
    private String bookSearchApi;
    //    @Value("${app.api.naver.search.movie}")
    private String movieSearchApi;

    //@Autowired
    private AppConfigManager config;
    //@Autowired
    //@Qualifier("rested")
    private RestTemplate restTemplate;
    private HttpEntity entity;

    public NaverService(RestTemplate template, AppConfigManager config) {
        // Autowired 안쓰고 생성자 주입하면 name으로 가져오지 않는다.. 동일한 타입의 Bean이 하나라서??
        this.restTemplate = template;
        this.config = config;
        // 생성자에서 셋팅할 경우 설정값을 yml에서 바로 가져오지 못한다...
        //createHttpHeader();
    }

    @Bean
    private void createHttpHeader() {
        // Header 정보 셋팅
        HttpHeaders headers = new HttpHeaders();
        //headers.add("X-Naver-Client-Id", this.clientId);
        //headers.add("X-Naver-Client-Secret", this.clientSecret);
        headers.add("X-Naver-Client-Id", config.getNaverApi().getClientId());
        headers.add("X-Naver-Client-Secret", config.getNaverApi().getClientSecret());
        this.entity = new HttpEntity(headers);
        this.bookSearchApi = config.getNaverApi().getSearch().get("book");
        this.movieSearchApi = config.getNaverApi().getSearch().get("movie");
    }

    public ResponseEntity<BookResponse> searchBook(BookRequest req) throws ApiException {
        log.info(req.toString());
        //createHttpHeader();
        String url = this.bookSearchApi; // 설정에서 가져오면 좋겠다
        // 인코딩 방법 1
        //headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        // 인코딩 방법 2 (이걸로는 쿼리가 되지 않는다)
        //String text = URLEncoder.encode("팩트풀니스", "UTF-8");
        // 인코딩 방법 3
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        builder.queryParam("query", req.getQuery());
        builder.queryParam("display", req.getDisplay());
        builder.queryParam("start", req.getStart());
        builder.queryParam("sort", req.getSort());
        UriComponents endUri = builder.build().encode(StandardCharsets.UTF_8);
        URI uri = endUri.toUri();

        // 객체로 응답을 받아 결과 를 보여준다
        ResponseEntity<BookResponse> response = this.restTemplate.exchange(uri, HttpMethod.GET, this.entity, BookResponse.class);
        log.info(response.getStatusCode().toString());
        //log.info(response.toString());

        if (response.getStatusCodeValue() != 200) {
            throw new ApiException("fail to call rest template", "10000", "error!!!");
        }

        return response;
    }

    public ResponseEntity<MovieResponse> searchMovie(MovieRequest req) throws ApiException {
        log.info(req.toString());
        //createHttpHeader();
        String url = this.movieSearchApi;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        builder.queryParam("query", req.getQuery());
        builder.queryParam("display", req.getDisplay());
        builder.queryParam("start", req.getStart());
        builder.queryParam("genre", req.getGenre());
        builder.queryParam("country", req.getCountry());
        builder.queryParam("yearfrom", req.getYearfrom());
        builder.queryParam("yearto", req.getYearto());

        UriComponents endUri = builder.build().encode(StandardCharsets.UTF_8);
        URI uri = endUri.toUri();
        // 객체로 응답을 받아 결과 를 보여준다
        ResponseEntity<MovieResponse> response = this.restTemplate.exchange(uri, HttpMethod.GET, this.entity, MovieResponse.class);
        log.info(response.getStatusCode().toString());
        //log.info(response.toString());

        if (response.getStatusCodeValue() != 200) {
            throw new ApiException("fail to call rest template", "10000", "error!!!");
        }

        // sorting 로직 추가 리플렉션 사용 / 람다로 comparator 구현
        try {
            if (req.getSortField().isEmpty() == false) {
                int sortMethod = req.getSortMethod();
                String field = new String("get").concat(req.getSortField().toLowerCase());
                response.getBody().getItems().sort((movie1, movie2) -> {
                    Object o1 = null;
                    Object o2 = null;

                    Class<? extends MovieResponse.Movie> movie = movie1.getClass();
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
                    if(sortMethod == 1) {
                        return o2.toString().compareTo(o1.toString());
                    } else {
                        return o1.toString().compareTo(o2.toString());
                    }

                });
            }
        } catch (Exception e) {
            throw new ApiException("fail to sort from user query", "10001", "sorting Error!!!");
        }


        return response;
    }


}
