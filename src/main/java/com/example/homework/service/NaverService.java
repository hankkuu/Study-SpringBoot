package com.example.homework.service;

import com.example.homework.common.ApiException;
import com.example.homework.model.request.BookRequest;
import com.example.homework.model.response.BookResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
public class NaverService {

    private final String clientId = "e8h7wbC8gB79115kRulB"; //애플리케이션 클라이언트 아이디값"
    private final String clientSecret = "di_bU_MKD4"; //애플리케이션 클라이언트 시크릿값"

    public ResponseEntity<BookResponse> searchBook(BookRequest req) throws ApiException {

        // connection pool 적용 언젠가 만날 것 같아서 기록..
        // RestTemplate 은 기본적으로 connection pool 을 사용하지 않는다. 따라서 연결할 때 마다, 로컬 포트를 열고 tcp connection 을 맺는다.
        // 이때 문제는 close() 이후에 사용된 소켓은 TIME_WAIT 상태가 되는데, 요청량이 많다면 이런 소켓들을 재사용하지 못하고 소켓이 오링나서 응답이 지연될 것이다.
        // 이런 경우 connection pool 을 사용해서 해결할 수 있는데, DBCP마냥 소켓의 갯수를 정해서 재사용하는 것이다.
        //  RestTemplate 에서 connection pool 을 적용하려면, 위와 같이 HttpClient 를 만들고 setHttpClient() 를 해야한다.
        //setMaxConnPerRoute : IP,포트 1쌍에 대해 수행 할 연결 수를 제한한다.
        //setMaxConnTotal : 최대 오픈되는 커넥션 수를 제한한다.
        //출처: https://sjh836.tistory.com/141 [빨간색코딩]
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000); // 읽기시간초과, ms
        factory.setConnectTimeout(3000); // 연결시간초과, ms
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100) // connection pool 적용
                .setMaxConnPerRoute(5) // connection pool 적용
                .build();
        factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
        RestTemplate restTemplate = new RestTemplate(factory);

        // Header 정보 셋팅
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", this.clientId);
        headers.add("X-Naver-Client-Secret", this.clientSecret);
        HttpEntity entity = new HttpEntity(headers);
        String url = "https://openapi.naver.com/v1/search/book.json"; // 설정에서 가져오면 좋겠다
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
        System.out.println(uri.getHost());
        System.out.println(uri.getPath());
        System.out.println(uri.getQuery());

        // test
        //url = url + "?" + "query=" + "팩트풀니스";

        // 객체로 응답을 받아 결과 를 보여준다
        ResponseEntity<BookResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, BookResponse.class);
        System.out.println(response.getStatusCode().value());

        if(response.getStatusCodeValue() != 200) {
            throw new ApiException("fail to call rest template", "10000", "error!!!");
        }

        return response;
    }
}
