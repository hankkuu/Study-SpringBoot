package com.example.homework.service;

import com.example.homework.common.ApiException;
import com.example.homework.config.RestTemplateConfig;
import com.example.homework.model.request.BookRequest;
import com.example.homework.model.response.BookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class NaverService {

    @Value("${api.naver.clientId}")
    private String clientId; //애플리케이션 클라이언트 아이디값"
    @Value("${api.naver.clientSecret}")
    private String clientSecret; //애플리케이션 클라이언트 시크릿값"
    @Value("${api.naver.search.book}")
    private String bookSearchApi;

    private final RestTemplateConfig restTemplateConfig;
    private RestTemplate restTemplate;
    private HttpEntity entity;

    public NaverService(RestTemplateConfig restTemplateConfig) {
        this.restTemplateConfig = restTemplateConfig;
        this.restTemplate = restTemplateConfig.restTemplate();
        createHttpHeader();
    }

    private void createHttpHeader() {
        // Header 정보 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", this.clientId);
        headers.add("X-Naver-Client-Secret", this.clientSecret);
        this.entity = new HttpEntity(headers);
    }

    public ResponseEntity<BookResponse> searchBook(BookRequest req) throws ApiException {
        log.info(req.toString());
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
        UriComponents endUri =  builder.build().encode(StandardCharsets.UTF_8);
        URI uri = endUri.toUri();
        log.info(uri.getHost());
        log.info(uri.getPath());
        log.info(uri.getQuery());

        // test
        //url = url + "?" + "query=" + "팩트풀니스";

        // 객체로 응답을 받아 결과 를 보여준다
        ResponseEntity<BookResponse> response = this.restTemplate.exchange(uri, HttpMethod.GET, this.entity, BookResponse.class);
        log.info(response.getStatusCode().toString());

        if(response.getStatusCodeValue() != 200) {
            throw new ApiException("fail to call rest template", "10000", "error!!!");
        }

        return response;
    }
}
