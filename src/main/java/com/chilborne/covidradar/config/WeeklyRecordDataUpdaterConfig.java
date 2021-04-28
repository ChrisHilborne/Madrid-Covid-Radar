package com.chilborne.covidradar.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpRequest;

@Configuration
@ConfigurationProperties(prefix = "data.url")
public class WeeklyRecordDataUpdaterConfig {

    private String update;

    @Bean("weeklyRecord-Update-Http-Request")
    public HttpRequest updateRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(update))
                .GET()
                .build();
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
