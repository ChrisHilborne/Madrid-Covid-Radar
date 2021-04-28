package com.chilborne.covidradar.data.collection.initialization;

import com.chilborne.covidradar.data.collection.DataFetcher;
import com.chilborne.covidradar.data.pipeline.PipelineManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Profile("!test")
@Component
public class WeeklyRecordDataInitializer implements DataInitializer {

    private final DataFetcher dataFetcher;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordDataInitializer.class);
    private final HttpRequest initializeRequest;
    private final PipelineManager initializePipeline;

    public WeeklyRecordDataInitializer(DataFetcher dataFetcher,
                                       @Qualifier("weeklyRecord-Initialize-Http-Request") HttpRequest initializeRequest,
                                       @Qualifier("initialize-Pipeline-Manager") PipelineManager initializePipeline) {
        this.dataFetcher = dataFetcher;
        this.initializeRequest = initializeRequest;
        this.initializePipeline = initializePipeline;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            initializeData();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void initializeData() throws Exception {
        logger.debug("Initialising WeeklyRecord data...");
        HttpResponse response = dataFetcher.fetchData(initializeRequest);
        String data = response.body().toString();
        initializePipeline.startPipeline(data);
        logger.debug("Data successfully initialised.");
    }
}
