package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyRecordsToHealthWardPipeline {

    private final Pipeline<List<DailyRecord>, List<HealthWard>> pipeline;

    private final Logger logger = LoggerFactory.getLogger(DailyRecordsToHealthWardPipeline.class);

    public DailyRecordsToHealthWardPipeline(@Qualifier("healthWard-Pipeline") Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public List<HealthWard> startPipeline(List<DailyRecord> dailyRecords) {
        logger.debug("Starting to convert dailyRecords (hashcode: " + dailyRecords.hashCode() + ") to DistrictData");

        return pipeline.execute(dailyRecords);
    }
}
