package com.chilborne.covidradar.config;

import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

import static org.junit.jupiter.api.Assertions.*;

class HttpWeeklyRecordFetcherConfigTest {

    HttpWeeklyRecordFetcherConfig config = new HttpWeeklyRecordFetcherConfig();

    @Test
    void httpClient() {
        //when
        Object result = config.httpClient();

        //assert
        assertTrue(result instanceof HttpClient);
    }
}