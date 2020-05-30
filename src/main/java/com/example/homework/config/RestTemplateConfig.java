package com.example.homework.config;

import com.example.homework.common.HomeworkRestTemplate;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    private HomeworkRestTemplate template = new HomeworkRestTemplate();

    @Bean(name = "rest")
    public HomeworkRestTemplate restTemplate() {
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100) // connection pool 적용
                .setMaxConnPerRoute(5) // connection pool 적용
                .build();

        this.factory.setReadTimeout(5000); // 읽기시간초과, ms
        this.factory.setConnectTimeout(3000); // 연결시간초과, ms
        this.factory.setHttpClient(httpClient); // 동기실행에 사용될 HttpClient 세팅
        template.setRequestFactory(this.factory);

        return template;
    }
}
