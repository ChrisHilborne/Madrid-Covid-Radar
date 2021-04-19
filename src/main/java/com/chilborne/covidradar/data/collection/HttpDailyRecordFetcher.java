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

/**
 * Fetches data from external API - either at program start-up or at set times to check for new data.
 * If data is new - it publishes the response body as a new data event which is picked up by the relevant pipeline.
 */
@Component
@Primary
public class HttpDailyRecordFetcher implements DataFetcher {


    private final HttpClient httpClient;
    private final Map<DataFetchAction, URI> uriMap;
    private final DataEventPublisher dataEventPublisher;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;

    private OffsetDateTime lastModified;
    private int dataHash;

    private final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    public HttpDailyRecordFetcher(HttpClient httpClient,
                                  Map<DataFetchAction, URI> uriMap,
                                  DataEventPublisher newDataEventPublisher) {
        this.httpClient = httpClient;
        this.uriMap = uriMap;
        this.dataEventPublisher = newDataEventPublisher;
    }

    @Override
    public void fetchData(DataFetchAction actionType) throws DataFetchException {
        logger.debug("Fetching data...");
        HttpRequest httpRequest = buildHttpRequest(actionType);
        HttpResponse<String> httpResponse = makeHttpRequest(httpRequest);

        if (dataIsNew(httpResponse)) {
            lastModified = getLastModified(httpResponse);
            dataHash = httpResponse.body().hashCode();
            publishData(httpResponse.body(), actionType);
        }
    }

    private boolean dataIsNew(HttpResponse httpResponse) {
        int fetchedDataHashCode = httpResponse.body() != null ?
                httpResponse.body().hashCode() :
                "".hashCode();

        if(httpResponse.statusCode() == 304) {
            logger.debug("No new data: 304 status code received.");
            return false;
        }
        else if (dataHash == fetchedDataHashCode) {
            logger.debug("No new data: hashcode for fetched data matched hashcode of last update.");
            return false;
        }
        return true;
    }

    private HttpResponse<String> makeHttpRequest(HttpRequest httpRequest) throws DataFetchException {
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

    private HttpRequest buildHttpRequest(DataFetchAction actionType) {
        logger.debug("Building HttpRequest type: " + actionType);
        HttpRequest request;
        if (lastModified != null) {
             request = HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(uriMap.get(actionType))
                    .header(HttpHeaders.IF_MODIFIED_SINCE,
                            dateTimeFormatter.format(lastModified))
                    .build();
        }
        else {
            request = HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(uriMap.get(actionType))
                    .build();
            }
        logger.debug(String.format("HttpRequest Built: headers = %s -- body = %s", request.headers().toString(), request.toString()));
        return request;
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
        dataEventPublisher.publishDataEvent(responseBody, action);
    }
}
