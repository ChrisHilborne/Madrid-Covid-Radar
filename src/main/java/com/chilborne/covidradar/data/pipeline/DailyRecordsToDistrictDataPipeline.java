package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyRecordsToDistrictDataPipeline {

    private final Pipeline<List<DailyRecord>, List<DistrictData>> pipeline;

    private final Logger logger = LoggerFactory.getLogger(DailyRecordsToDistrictDataPipeline.class);

    public DailyRecordsToDistrictDataPipeline(@Qualifier("districtData-Pipeline") Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public List<DistrictData> startPipeline(List<DailyRecord> dailyRecords) {
        logger.debug("Starting to convert dailyRecords (hashcode: " + dailyRecords.hashCode() + ") to DistrictData");

        return pipeline.execute(dailyRecords);
    }
}
