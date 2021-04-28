package com.chilborne.covidradar.data.collection;

import com.chilborne.covidradar.exceptions.DataFetchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Fetches data from external API - either at program start-up or at set times to check for new data.
 * If data is new - it publishes the response body as a new data event which is picked up by the relevant pipeline.
 */
@Component
@Primary
public class HttpWeeklyRecordFetcher implements DataFetcher<String> {


    private final HttpClient httpClient;
    private final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    public HttpWeeklyRecordFetcher(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public HttpResponse<String> fetchData(HttpRequest httpRequest) throws DataFetchException {
        try {
            logger.debug("Making HTTP Request: " + httpRequest.toString());
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            logger.debug("HTTP Request successfully made - data received.");
            logger.debug(String.format("HTTP Response Headers: %s", httpResponse.headers().toString()));
            return httpResponse;

        } catch (IOException e) {
            logger.error("Problem when making HTTP Request", e);
            throw new DataFetchException(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error("HTTP Request interrupted", e);
            throw new DataFetchException(e.getMessage(), e);
        }
    }


}
