package com.chilborne.covidradar.data.collection;

import com.chilborne.covidradar.events.NewDataEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


@Component
@Primary
@Profile(value = "dynamic")
public class HttpDailyRecordFetcher implements DailyRecordDataFetcher<HttpResponse<String>> {


    private final HttpClient httpClient;
    private final URI uri;
    private final NewDataEventPublisher newDataEventPublisher;
    private final DateTimeFormatter dateTimeFormatter;
    private OffsetDateTime lastModified;

    private final Logger logger = LoggerFactory.getLogger(DailyRecordDataFetcher.class);

    public HttpDailyRecordFetcher(HttpClient httpClient,
                                  DateTimeFormatter dateTimeFormatter,
                                  URI uri,
                                  NewDataEventPublisher newDataEventPublisher) {
        this.httpClient = httpClient;
        this.dateTimeFormatter = dateTimeFormatter;
        this.uri = uri;
        this.newDataEventPublisher = newDataEventPublisher;
    }

    @Override
    public void collectData() throws IOException, InterruptedException {
        logger.debug("Starting to fetch data from API...");
        HttpRequest httpRequest = getRequest();
        logger.debug("Sending HttpRequest...");
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        logger.debug("Data successfully received.");

        if (httpResponse.statusCode() == 304) {
            logger.debug("No New Data Available");
            return;
        }

        lastModified = OffsetDateTime.parse(
                httpResponse
                        .headers()
                        .map()
                        .get("Last-Modified").get(0),
                dateTimeFormatter
        );

        publish(httpResponse);
    }

    private HttpRequest getRequest() {
        logger.debug("Building HttpRequest...");
        if (lastModified != null) {
            return HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(uri)
                    .header("If-Modified-Since", dateTimeFormatter.format(lastModified))
                    .build();
        } else {
            return HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(uri)
                    .build();
        }
    }

    @Override
    public void publish(HttpResponse<String> httpResponse) {
        newDataEventPublisher.publishNewDataEvent(httpResponse.body());
    }
}
