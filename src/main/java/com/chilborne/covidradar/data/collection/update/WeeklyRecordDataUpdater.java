package com.chilborne.covidradar.data.collection.update;

import com.chilborne.covidradar.data.collection.DataFetcher;
import com.chilborne.covidradar.data.pipeline.PipelineManager;
import com.chilborne.covidradar.exceptions.DataFetchException;
import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class WeeklyRecordDataUpdater implements DataUpdater {

    private final DataFetcher<String> dataFetcher;
    private final HttpRequest updateRequest;
    private final PipelineManager<HttpResponse<String>, List<WeeklyRecord>> updatePipeline;


    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordDataUpdater.class);

    public WeeklyRecordDataUpdater(DataFetcher<String> dataFetcher,
                                   @Qualifier("weeklyRecord-Update-Http-Request") HttpRequest updateRequest,
                                   @Qualifier("update-Pipeline-Manager") PipelineManager<HttpResponse<String>, List<WeeklyRecord>> updatePipeline) {
        this.dataFetcher = dataFetcher;
        this.updateRequest = updateRequest;
        this.updatePipeline = updatePipeline;
    }

    @Override
    @Scheduled(cron = "0 0 6,9,12,15,18,21 ? * MON-FRI", zone = "Europe/Paris")
    public void updateData() {
        logger.debug("Updating WeeklyRecord data...");
        try {
            HttpResponse<String> response = dataFetcher.fetchData(updateRequest);
            updatePipeline.startPipeline(response);
        } catch (DataFetchException | PipeLineProcessException exception) {
            logger.error(exception.getMessage(), exception);
        }
        logger.debug("WeeklyRecord Successfully updated");
    }
}
