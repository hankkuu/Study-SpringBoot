package com.example.homework.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppConfigManager {

    private NaverApi naverApi = new NaverApi();

    @Getter
    @Setter
    public class NaverApi {
        private String clientId;
        private String clientSecret;
        private Map<String, String> search;
    }
}


