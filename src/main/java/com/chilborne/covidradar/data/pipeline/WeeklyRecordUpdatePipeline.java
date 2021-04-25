package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.events.UpdatedDataEvent;
import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class WeeklyRecordUpdatePipeline {

    private final Pipeline updatePipeline;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordInitalizePipeline.class);

    public WeeklyRecordUpdatePipeline(
            @Qualifier("weeklyRecord-update-pipeline") Pipeline updatePipeline) {
        this.updatePipeline = updatePipeline;
    }

    @EventListener
    public void startUpdatePipeline(UpdatedDataEvent updatedDataEvent) {
        logger.info("Starting WeeklyRecordUpdatePipeline...");
        try {
            updatePipeline.execute(updatedDataEvent.getData());
        } catch (PipeLineProcessException pipeLineProcessException) {
            logger.error(pipeLineProcessException.getMessage(), pipeLineProcessException);
        }
    }




}
