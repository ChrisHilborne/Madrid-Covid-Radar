package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.events.NewDataEvent;
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

    private final Pipeline<String, List<DailyRecord>> pipeline;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordProcessingPipeline.class);

    public DailyRecordProcessingPipeline(@Qualifier("dailyRecord-Pipeline") Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @EventListener
    public void startPipeline(NewDataEvent newDataEvent) {
        try {
            pipeline.execute(newDataEvent.getData());
        } catch (PipeLineProcessException pipeLineProcessException) {
            logger.error(pipeLineProcessException.getMessage(), pipeLineProcessException);
        }
    }




}
