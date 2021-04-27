package com.chilborne.covidradar.data.collection;

import com.chilborne.covidradar.events.DataEventPublisher;
import com.chilborne.covidradar.exceptions.DataFetchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * Fetches data from external API - either at program start-up or at set times to check for new data.
 * If data is new - it publishes the response body as a new data event which is picked up by the relevant pipeline.
 */
@Component
@Primary
public class HttpWeeklyRecordFetcher implements DataFetcher {


    private final HttpClient httpClient;
    private final Map<DataFetchAction, URI> uriMap;
    private final DataEventPublisher dataEventPublisher;

    private String lastDataEtag = "1";

    private final Logger logger = LoggerFactory.getLogger(DataFetcher.class);

    public HttpWeeklyRecordFetcher(HttpClient httpClient,
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
            lastDataEtag = getEtag(httpResponse);
            publishData(httpResponse.body(), actionType);
        }
    }

    private boolean dataIsNew(HttpResponse httpResponse) {
        if(lastDataEtag.equals(getEtag(httpResponse))) {
            logger.debug("No new data: etags match.");
            return false;
        }
        logger.debug("***** Data is New *****");
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
        URI uri = uriMap.get(actionType);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        logger.debug(String.format("HttpRequest Built: headers = %s -- body = %s", request.headers().toString(), request.toString()));
        return request;
    }

    private String getEtag(HttpResponse<String> httpResponse) {
        return httpResponse
                        .headers()
                        .map()
                        .get("etag").get(0);
    }

    @Override
    public void publishData(String responseBody, DataFetchAction action) {
        dataEventPublisher.publishDataEvent(responseBody, action);
    }
}
