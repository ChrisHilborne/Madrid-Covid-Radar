package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Processes the data before it is served through the Rest API
 */
@Service
public class WeeklyRecordsToHealthWardPipeline {

    private final Pipeline<List<WeeklyRecord>, List<HealthWard>> pipeline;

    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordsToHealthWardPipeline.class);

    public WeeklyRecordsToHealthWardPipeline(@Qualifier("weeklyRecord-to-healthWard-Pipeline") Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public List<HealthWard> startPipeline(List<WeeklyRecord> weeklyRecords) {
        logger.debug("Starting to convert WeeklyRecords (hashcode: " + weeklyRecords.hashCode() + ") to HealthWard");

        return pipeline.execute(weeklyRecords);
    }
}
