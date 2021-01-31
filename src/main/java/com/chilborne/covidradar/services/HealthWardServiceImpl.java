package com.chilborne.covidradar.services;

import com.chilborne.covidradar.data.pipeline.DailyRecordsToHealthWardPipeline;
import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HealthWardServiceImpl implements HealthWardService {

    private final DailyRecordService healthWardService;
    private final DailyRecordsToHealthWardPipeline healthWardPipeline;
    private final Logger logger = LoggerFactory.getLogger(HealthWardServiceImpl.class);

    public HealthWardServiceImpl(DailyRecordService dailyRecordService,
                                 DailyRecordsToHealthWardPipeline healthWardPipeline) {
        this.healthWardService = dailyRecordService;
        this.healthWardPipeline = healthWardPipeline;
    }

    @Override
    @Cacheable("healthWard-all")
    public List<HealthWard> getAllHealthWards() throws DataNotFoundException {
        logger.debug("Fetching All Health Wards");
        List<DailyRecord> data = healthWardService.getAll();
        return healthWardPipeline.startPipeline(data);
    }

    @Override
    @Cacheable("healthWard-name")
    public HealthWard getHealthWardByName(String name) throws DataNotFoundException {
        logger.debug("Fetching Data for: " + name);
        List<DailyRecord> dailyRecords = healthWardService.getDailyRecordsByHealthWard(name);

        return new HealthWard(dailyRecords);
    }

    @Override
    @Cacheable("healthWard-geoCode")
    public HealthWard getHealthWardByGeoCode(String geoCode) throws DataNotFoundException {
        logger.debug("Fetching data for GeoCode: " + geoCode);
        List<DailyRecord> dailyRecords = healthWardService.getDailyRecordsByGeoCode(geoCode);

        return new HealthWard(dailyRecords);
    }

    @Override
    @Cacheable("namesAndGeoCodes")
    public Map<String, String> getHealthWardGeoCodesAndNames() {
        Map<String, String> namesAndGeocodes = new HashMap<>();

        List<HealthWard> allData = getAllHealthWards();
        allData.forEach(healthWard -> {
            namesAndGeocodes.put(healthWard.getName(), healthWard.getGeoCode());
        });

        return namesAndGeocodes;
    }




}
