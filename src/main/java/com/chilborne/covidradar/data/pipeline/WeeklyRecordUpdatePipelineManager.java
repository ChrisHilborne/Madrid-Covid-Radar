package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;

@Service("update-Pipeline-Manager")
public class WeeklyRecordUpdatePipelineManager implements PipelineManager<HttpResponse<String>, List<WeeklyRecord>> {

    private final Pipeline<HttpResponse<String>, List<WeeklyRecord>> updatePipeline;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordInitalizePipelineManager.class);

    public WeeklyRecordUpdatePipelineManager(
            @Qualifier("weeklyRecord-update-pipeline") Pipeline updatePipeline) {
        this.updatePipeline = updatePipeline;
    }


    @Override
    public List<WeeklyRecord> startPipeline(HttpResponse<String> input) {
        logger.debug("Starting Update Pipeline");
        try {
            return updatePipeline.execute(input);
        }
        catch (PipeLineProcessException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
