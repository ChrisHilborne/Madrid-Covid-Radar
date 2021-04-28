package com.chilborne.covidradar.data.steps;


import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;

@Component
public class HttpResponseValidator implements Step<HttpResponse<String>, String> {

    private String mostRecentNewEtag = "0";
    private final Logger logger = LoggerFactory.getLogger(HttpResponseValidator.class);

    @Override
    public String process(HttpResponse<String> input) throws PipeLineProcessException {
        logger.debug("Checking if data is new...");
        String etag = getEtag(input.headers());

        if (etag.equals(mostRecentNewEtag)) {
            logger.debug("Data is not new -- etags match");
            throw new PipeLineProcessException("No New Data");
        }
        else {
            logger.debug("Data is new -- etags do not match");
            mostRecentNewEtag = etag;
            return input.body();
        }
    }

    private String getEtag(HttpHeaders headers) {
        return headers.map()
                .get("etag")
                .get(0);
    }
}
