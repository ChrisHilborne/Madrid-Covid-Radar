package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.collection.DataFetchAction;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.SECONDS;

@Configuration
@ConfigurationProperties(prefix = "data.url")
public class HttpWeeklyRecordFetcherConfig {

    private String init;
    private String update;

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.of(10, SECONDS))
                .build();
    }

    @Bean
    public Map<DataFetchAction, URI> uriMap() {
        HashMap<DataFetchAction, URI> uriMap = new HashMap<>();
        uriMap.put(DataFetchAction.INIT, URI.create(init));
        uriMap.put(DataFetchAction.UPDATE, URI.create(update));
        return uriMap;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public void setUpdate(String update) { this.update = update; }
}
