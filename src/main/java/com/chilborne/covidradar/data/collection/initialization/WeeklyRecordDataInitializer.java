package com.chilborne.covidradar.data.collection.initialization;

import com.chilborne.covidradar.data.collection.DataFetcher;
import com.chilborne.covidradar.data.pipeline.PipelineManager;
import com.chilborne.covidradar.exceptions.DataFetchException;
import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Profile("!test")
@Component
public class WeeklyRecordDataInitializer implements DataInitializer {

    private final DataFetcher dataFetcher;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordDataInitializer.class);
    private final HttpRequest initializeRequest;
    private final PipelineManager<String, List<WeeklyRecord>> initializePipeline;

    public WeeklyRecordDataInitializer(DataFetcher dataFetcher,
                                       @Qualifier("weeklyRecord-Initialize-Http-Request") HttpRequest initializeRequest,
                                       @Qualifier("initialize-Pipeline-Manager") PipelineManager<String, List<WeeklyRecord>> initializePipeline) {
        this.dataFetcher = dataFetcher;
        this.initializeRequest = initializeRequest;
        this.initializePipeline = initializePipeline;
    }

    @Override
    public void initializeData() {
        logger.debug("Initialising WeeklyRecord data...");
        try {
            HttpResponse response = dataFetcher.fetchData(initializeRequest);
            String data = response.body().toString();
            initializePipeline.startPipeline(data);
        } catch (DataFetchException | PipeLineProcessException exception) {
            logger.error(exception.getMessage(), exception);
        }

        logger.debug("Data successfully initialised.");
    }
}
