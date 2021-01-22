package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.events.NewDataEvent;
import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DailyRecordDataPipeline {

    private final Pipeline pipeline;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordDataPipeline.class);

    public DailyRecordDataPipeline(Pipeline pipeline) {
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
