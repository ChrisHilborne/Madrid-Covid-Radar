package com.chilborne.covidradar.data.collection;

import com.chilborne.covidradar.exceptions.DataFetchException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

class HttpWeeklyRecordFetcherTest {

    MockWebServer mockWebServer;

    HttpClient client;

    HttpWeeklyRecordFetcher fetcher;

    @BeforeEach
    void init() {
        mockWebServer = new MockWebServer();
        client = HttpClient.newHttpClient();
        fetcher = new HttpWeeklyRecordFetcher(client);
    }

    @Test
    void fetchData_Response_200() throws IOException, InterruptedException {
        //given
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody("TEST_RESPONSE_BODY")
                .setResponseCode(200));

        URI mockServerURI = mockWebServer.url("/").uri();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(mockServerURI)
                .GET()
                .build();

        //when
        HttpResponse<String> response = fetcher.fetchData(request);

        //verify
        assertEquals(200, response.statusCode());
        assertEquals("TEST_RESPONSE_BODY", response.body());
    }

    @Test
    void fetchData_Response_500() throws IOException, InterruptedException {
        //given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500));

        URI mockServerURI = mockWebServer.url("/").uri();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(mockServerURI)
                .GET()
                .build();

        //verify
        Exception e = assertThrows(DataFetchException.class,
                () -> fetcher.fetchData(request));
        Assertions.assertEquals("HttpResponse Status Code is: 500", e.getMessage());
    }
}