package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.events.InitialDataEvent;
import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Processes the data when it is fetched from the external service.
 * Initial data is daily. Because update data is weekly - we need to transform initial data to weekly and thus this data is passed to a different pipeline.
 */
@Service
public class DailyRecordInitalizePipeline {

    private final Pipeline<String, List<DailyRecord>> initializePipeline;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordInitalizePipeline.class);

    public DailyRecordInitalizePipeline(
            @Qualifier("dailyRecord-initialize-pipeline") Pipeline initializePipeline) {
        this.initializePipeline = initializePipeline;
    }

    @EventListener
    public void startInitializePipeline(InitialDataEvent initialDataEvent) {
        try {
            logger.info("Starting DailyRecordPipeline - INIT...");
            initializePipeline.execute(initialDataEvent.getData());
        } catch (PipeLineProcessException pipeLineProcessException) {
            logger.error(pipeLineProcessException.getMessage(), pipeLineProcessException);
        }
    }





}
