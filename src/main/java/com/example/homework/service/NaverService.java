package com.example.homework.service;

import com.example.homework.common.exception.ApiException;
import com.example.homework.common.HomeworkRestTemplate;
import com.example.homework.common.util.Convertor;
import com.example.homework.config.AppConfigManager;
import com.example.homework.model.request.BookRequest;
import com.example.homework.model.request.MovieRequest;
import com.example.homework.model.response.BookResponse;
import com.example.homework.model.response.MovieResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Slf4j
@Service
public class NaverService {

    private AppConfigManager config;
    private HomeworkRestTemplate restTemplate;
    private HttpEntity entity;

    public NaverService(HomeworkRestTemplate template, AppConfigManager config) {
        this.restTemplate = template;
        this.config = config;
    }

    @Bean
    private void createHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", config.getNaverApi().getClientId());
        headers.add("X-Naver-Client-Secret", config.getNaverApi().getClientSecret());
        this.entity = new HttpEntity(headers);
    }

    private String createQueryString(String url, Object o) throws ApiException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        Map<String, Object> map = Convertor.converObjectToMap(o);
        map.forEach((key, value) -> builder.queryParam(key, value.toString()) );
        UriComponents endUri = builder.build(); //.encode(StandardCharsets.UTF_8); //encoding 안됨
        URI uri = endUri.toUri();
        return uri.toString();
    }

    public ResponseEntity<BookResponse> searchBook(BookRequest req) throws ApiException, IllegalAccessException {
        String uri = config.getNaverApi().getSearch().get("book");
        uri = this.createQueryString(uri, req);
        ResponseEntity<BookResponse> response = this.restTemplate.exchange(uri, HttpMethod.GET, this.entity, BookResponse.class, BookResponse.Book.class);

        if (response.getStatusCodeValue() == 200) {
            throw new ApiException("fail to call rest template - book", 20000, "error!!!");
        }

        // RestTemplate 에서 구현하지 않고 따로 응답 클래스에서 구현할 경우
        response.getBody().excludeHtmlTag(response.getBody().getItems());

        if (req.getSortField().isEmpty() == false) {
            req.sortResponseEntity(response.getBody().getItems());
        }

        return response;
    }

    public ResponseEntity<MovieResponse> searchMovie(MovieRequest req) throws ApiException {
        String uri = config.getNaverApi().getSearch().get("movie");
        uri = this.createQueryString(uri, req);
        // RestTemplate 내에서 구현
        ResponseEntity<MovieResponse> response = this.restTemplate.exchangeExcludeHtmlTag(uri, HttpMethod.GET, this.entity, MovieResponse.class, MovieResponse.Movie.class);

        if (response.getStatusCodeValue() == 200) {
            throw new ApiException("fail to call rest template - movie", 10000, "error!!!");
        }

        if(req.isIgnoreZeroRating() == true) {
            // sort와 달리 범용적으로 쓰는 비즈니스가 아니다.. 이 경우는 서비스에서 직접 구현한다
            response.getBody().getItems().removeIf(x -> x.getUserRating().doubleValue() == 0.0);
        }

        if (req.getSortField().isEmpty() == false) {
            req.sortResponseEntity(response.getBody().getItems());
        }

        return response;
    }
}
