package com.chilborne.covidradar.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpRequest;

@Configuration
@ConfigurationProperties(prefix = "data.url")
public class WeeklyRecordDataInitializerConfig {

    private String init;

    @Bean("weeklyRecord-Initialize-Http-Request")
    public HttpRequest initializeRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(init))
                .GET()
                .build();
    }

    public void setInit(String init) {
        this.init = init;
    }
}
