package com.chilborne.covidradar.data.collection.update;

import com.chilborne.covidradar.data.collection.DataFetcher;
import com.chilborne.covidradar.data.pipeline.PipelineManager;
import com.chilborne.covidradar.exceptions.DataFetchException;
import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeeklyRecordDataUpdater implements DataUpdater {

    private final DataFetcher dataFetcher;
    private final HttpRequest updateRequest;
    private final PipelineManager updatePipeline;


    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordDataUpdater.class);

    public WeeklyRecordDataUpdater(DataFetcher dataFetcher,
                                   @Qualifier("weeklyRecord-Update-Http-Request") HttpRequest updateRequest,
                                   @Qualifier("update-Pipeline-Manager") PipelineManager updatePipeline) {
        this.dataFetcher = dataFetcher;
        this.updateRequest = updateRequest;
        this.updatePipeline = updatePipeline;
    }

    @Override
    @Scheduled(cron = "0 0 6,9,12,15,18,21 ? * *")
    public void updateData() {
        logger.debug("Updating WeeklyRecord data...");
        try {
            HttpResponse response = dataFetcher.fetchData(updateRequest);
            updatePipeline.startPipeline(response);
        } catch (DataFetchException | PipeLineProcessException exception) {
            logger.error(exception.getMessage(), exception);
        }
        logger.debug("WeeklyRecord Successfully updated");
    }
}
