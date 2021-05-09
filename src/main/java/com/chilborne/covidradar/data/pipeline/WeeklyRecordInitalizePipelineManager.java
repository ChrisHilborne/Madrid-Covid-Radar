package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Processes the initial data when it is fetched from the external service.
 * Initial data is daily. Because update data is weekly - we need to transform initial daily data to weekly and thus this data is passed to a different pipeline.
 */
@Service("initialize-Pipeline-Manager")
public class WeeklyRecordInitalizePipelineManager implements PipelineManager<String> {

    private final Pipeline<String, List<WeeklyRecord>> initializePipeline;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordInitalizePipelineManager.class);

    public WeeklyRecordInitalizePipelineManager(
            @Qualifier("weeklyRecord-initialize-pipeline") Pipeline initializePipeline) {
        this.initializePipeline = initializePipeline;
    }

    public void startPipeline(String input) {
        initializePipeline.execute(input);
    }





}
