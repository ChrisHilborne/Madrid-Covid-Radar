package com.chilborne.covidradar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Configuration
public class HttpWeeklyRecordFetcherConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.of(10, SECONDS))
                .build();
    }

}
