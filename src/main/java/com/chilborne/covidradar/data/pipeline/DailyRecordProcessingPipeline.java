package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.events.InitialDataEvent;
import com.chilborne.covidradar.events.UpdatedDataEvent;
import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyRecordProcessingPipeline {

    private final Pipeline<String, List<DailyRecord>> initializePipeline;
    private final Pipeline<String, List<DailyRecord>> updatePipeline;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordProcessingPipeline.class);

    public DailyRecordProcessingPipeline(
            @Qualifier("dailyRecord-initialize-pipeline") Pipeline initializePipeline,
            @Qualifier("dailyRecord-update-pipeline") Pipeline updatePipeline) {
        this.initializePipeline = initializePipeline;
        this.updatePipeline = updatePipeline;
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

    @EventListener
    public void startUpdatePipeline(UpdatedDataEvent updatedDataEvent) {
        logger.info("Starting DailyRecordPipeline - UPDATE...");
        try {
            updatePipeline.execute(updatedDataEvent.getData());
        } catch (PipeLineProcessException pipeLineProcessException) {
            logger.error(pipeLineProcessException.getMessage(), pipeLineProcessException);
        }
    }




}
