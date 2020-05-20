package com.example.homework.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    private RestTemplate template = new RestTemplate();

    // @Bean 으로 설정시 자동 Singleton이 되는지 반복해서 호출해도 계속 호출되지 않음
    @Bean
    public RestTemplate restTemplate() {
        // connection pool 적용 언젠가 만날 것 같아서 기록..
        // RestTemplate 은 기본적으로 connection pool 을 사용하지 않는다. 따라서 연결할 때 마다, 로컬 포트를 열고 tcp connection 을 맺는다.
        // 이때 문제는 close() 이후에 사용된 소켓은 TIME_WAIT 상태가 되는데, 요청량이 많다면 이런 소켓들을 재사용하지 못하고 소켓이 오링나서 응답이 지연될 것이다.
        // 이런 경우 connection pool 을 사용해서 해결할 수 있는데, DBCP마냥 소켓의 갯수를 정해서 재사용하는 것이다.
        //  RestTemplate 에서 connection pool 을 적용하려면, 위와 같이 HttpClient 를 만들고 setHttpClient() 를 해야한다.

        //setMaxConnPerRoute : IP,포트 1쌍에 대해 수행 할 연결 수를 제한한다.
        //setMaxConnTotal : 최대 오픈되는 커넥션 수를 제한한다.
        //출처: https://sjh836.tistory.com/141 [빨간색코딩]
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100) // connection pool 적용
                .setMaxConnPerRoute(5) // connection pool 적용
                .build();

        //this.factory = new HttpComponentsClientHttpRequestFactory();
        this.factory.setReadTimeout(5000); // 읽기시간초과, ms
        this.factory.setConnectTimeout(3000); // 연결시간초과, ms
        this.factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
        template.setRequestFactory(this.factory);

        return template;
    }
}
