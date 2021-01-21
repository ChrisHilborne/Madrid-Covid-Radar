package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.events.NewDataEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DailyRecordDataPipeline {

    private final Pipeline pipeline;

    public DailyRecordDataPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @EventListener
    public void startPipeline(NewDataEvent newDataEvent) {
        pipeline.execute(newDataEvent.getData());
    }




}
