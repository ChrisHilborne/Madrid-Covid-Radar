package com.chilborne.covidradar.services;

import com.chilborne.covidradar.data.pipeline.DailyRecordsToHealthWardPipeline;
import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class HealthWardServiceImpl implements HealthWardService {

    private final DailyRecordService dailyRecordService;
    private final DailyRecordsToHealthWardPipeline healthWardPipeline;
    private final Logger logger = LoggerFactory.getLogger(HealthWardServiceImpl.class);

    public HealthWardServiceImpl(DailyRecordService dailyRecordService,
                                 DailyRecordsToHealthWardPipeline healthWardPipeline) {
        this.dailyRecordService = dailyRecordService;
        this.healthWardPipeline = healthWardPipeline;
    }

    @Override
    @Cacheable("healthWard-all")
    public List<HealthWard> getAllHealthWards() throws DataNotFoundException {
        logger.debug("Fetching All Health Wards");
        List<DailyRecord> data = dailyRecordService.getAll();
        return healthWardPipeline.startPipeline(data);
    }


    @Override
    @Cacheable("healthWard-geoCode")
    public HealthWard getHealthWardByGeoCode(String geoCode) throws DataNotFoundException {
        logger.debug("Fetching data for GeoCode: " + geoCode);
        List<DailyRecord> dailyRecords = dailyRecordService.getDailyRecordsByGeoCode(geoCode);

        return healthWardPipeline.startPipeline(dailyRecords).get(0);
    }

    @Override
    @Cacheable("namesAndGeoCodes")
    public Map<String, String> getHealthWardGeoCodesAndNames() {
        logger.debug("Fetching names and geocodes");
        return dailyRecordService.getNamesAndGeoCodes();
    }

}
