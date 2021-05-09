package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service("update-Pipeline-Manager")
public class WeeklyRecordUpdatePipeline implements PipelineManager<HttpResponse<String>> {

    private final Pipeline updatePipeline;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordInitalizePipelineManager.class);

    public WeeklyRecordUpdatePipeline(
            @Qualifier("weeklyRecord-update-pipeline") Pipeline updatePipeline) {
        this.updatePipeline = updatePipeline;
    }


    @Override
    public void startPipeline(HttpResponse<String> input) {
        logger.debug("Starting Update Pipeline");
        try {
            updatePipeline.execute(input);
            logger.debug("Update Pipeline Successfully Executed");
        }
        catch (PipeLineProcessException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
