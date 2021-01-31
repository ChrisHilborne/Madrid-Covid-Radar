package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.collection.DataFetchType;
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
public class HttpDailyRecordFetcherConfig {

    private String init;
    private String update;

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.of(10, SECONDS))
                .build();
    }

    @Bean
    public Map<DataFetchType, URI> uriMap() {
        HashMap<DataFetchType, URI> uriMap = new HashMap<>();
        uriMap.put(DataFetchType.INIT, URI.create(init));
        uriMap.put(DataFetchType.UPDATE, URI.create(update));
        return uriMap;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public void setUpdate(String update) { this.update = update; }
}
