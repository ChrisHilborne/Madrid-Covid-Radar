package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

class HttpResponseValidatorTest {

    HttpResponseValidator validator;

    HttpClient client;

    MockWebServer mockWebServer;

    @BeforeEach
    void init() {
        mockWebServer = new MockWebServer();
        client = HttpClient.newHttpClient();
        validator = new HttpResponseValidator();
    }

    @Test
    void process_new_Etag() throws IOException, InterruptedException {
        //given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("etag", "1")
                .setBody("NEW ETAG")
        );

        URI mockServerUri = mockWebServer.url("/").uri();

        HttpRequest request = HttpRequest
                .newBuilder(mockServerUri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //when
        String validatedResponse = validator.process(response);

        //verify
        assertEquals("NEW ETAG", validatedResponse);

    }

    @Test
    void process_old_Etag() throws IOException, InterruptedException {
        //given
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .addHeader("etag", "0")
                .setBody("NEW ETAG")
        );

        URI mockServerUri = mockWebServer.url("/").uri();

        HttpRequest request = HttpRequest
                .newBuilder(mockServerUri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //when
        Exception exception = assertThrows(PipeLineProcessException.class,
                () -> validator.process(response));

        //verify
        assertEquals("No New Data", exception.getMessage());

    }
}