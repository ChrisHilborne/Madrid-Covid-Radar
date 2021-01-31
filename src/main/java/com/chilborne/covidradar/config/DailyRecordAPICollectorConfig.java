package com.chilborne.covidradar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.SECONDS;

@Configuration
@ConfigurationProperties(prefix = "data")
public class DailyRecordAPICollectorConfig {

    private String url;

    @Bean
    public HttpRequest httpRequest() {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.of(10, SECONDS))
                .build();
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.RFC_1123_DATE_TIME;
    }

    @Bean
    public URI uri() {
        return URI.create(url);
    }


    public void setUrl(String url) {
        this.url = url;
    }
}
