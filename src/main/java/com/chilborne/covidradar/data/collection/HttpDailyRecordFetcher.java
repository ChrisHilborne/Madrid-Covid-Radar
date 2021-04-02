package com.chilborne.covidradar.data.collection;

import com.chilborne.covidradar.events.DataEventPublisher;
import com.chilborne.covidradar.exceptions.DataFetchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@Component
@Primary
public class HttpDailyRecordFetcher implements DataFetcher {


    private final HttpClient httpClient;
    private final Map<DataFetchAction, URI> uriMap;
    private final DataEventPublisher newDataEventPublisher;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;

    private OffsetDateTime lastModified;

    private final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    public HttpDailyRecordFetcher(HttpClient httpClient,
                                  Map<DataFetchAction, URI> uriMap,
                                  DataEventPublisher newDataEventPublisher) {
        this.httpClient = httpClient;
        this.uriMap = uriMap;
        this.newDataEventPublisher = newDataEventPublisher;
    }

    @Override
    public void fetchData(DataFetchAction action) throws DataFetchException {
        logger.debug("Fetching data...");
        HttpRequest httpRequest = buildHttpRequest(action);
        HttpResponse<String> httpResponse = makeHttpRequest(httpRequest);

        if(httpResponse.statusCode() == 304) {
            logger.info("Data has not been updated since it was last received.");
            return;
        }
        lastModified = getLastModified(httpResponse);
        publishData(httpResponse.body(), action);
    }

    private HttpResponse<String> makeHttpRequest(HttpRequest initialHttpRequest) throws DataFetchException {
        try {
            logger.debug("Making HttpRequest");
            HttpResponse<String> httpResponse = httpClient.send(initialHttpRequest, HttpResponse.BodyHandlers.ofString());
            logger.debug("HttpRequest successfully made - data received.");
            return httpResponse;

        } catch (IOException e) {
            logger.error("Problem when making Http Request", e);
            throw new DataFetchException(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error("Http Request interrupted", e);
            throw new DataFetchException(e.getMessage(), e);
        }
    }

    private HttpRequest buildHttpRequest(DataFetchAction type) {
        logger.debug("Building HttpRequest type: " + type);
        if (lastModified != null) {
            return HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(uriMap.get(type))
                    .header(HttpHeaders.IF_MODIFIED_SINCE,
                            dateTimeFormatter.format(lastModified))
                    .build();
        }
        else {
            return HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(uriMap.get(type))
                    .build();
            }
    }

    private OffsetDateTime getLastModified(HttpResponse<String> httpResponse) {
        return OffsetDateTime.parse(
                httpResponse
                        .headers()
                        .map()
                        .get("Last-Modified").get(0),
                dateTimeFormatter
        );
    }

    @Override
    public void publishData(String responseBody, DataFetchAction action) {
        newDataEventPublisher.publishDataEvent(responseBody, action);
    }
}
