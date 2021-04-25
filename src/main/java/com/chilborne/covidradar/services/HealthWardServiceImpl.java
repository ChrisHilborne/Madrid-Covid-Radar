package com.chilborne.covidradar.services;

import com.chilborne.covidradar.data.pipeline.WeeklyRecordsToHealthWardPipeline;
import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class HealthWardServiceImpl implements HealthWardService {

    private final WeeklyRecordService healthWardService;
    private final WeeklyRecordsToHealthWardPipeline healthWardPipeline;
    private final Logger logger = LoggerFactory.getLogger(HealthWardServiceImpl.class);

    public HealthWardServiceImpl(WeeklyRecordService weeklyRecordService,
                                 WeeklyRecordsToHealthWardPipeline healthWardPipeline) {
        this.healthWardService = weeklyRecordService;
        this.healthWardPipeline = healthWardPipeline;
    }

    @Override
    @Cacheable("healthWard-all")
    public List<HealthWard> getAllHealthWards() throws DataNotFoundException {
        logger.debug("Fetching All Health Wards");
        List<WeeklyRecord> data = healthWardService.getAll();
        return healthWardPipeline.startPipeline(data);
    }


    @Override
    @Cacheable("healthWard-geoCode")
    public HealthWard getHealthWardByGeoCode(String geoCode) throws DataNotFoundException {
        logger.debug("Fetching data for GeoCode: " + geoCode);
        List<WeeklyRecord> weeklyRecords = healthWardService.getWeeklyRecordsByGeoCode(geoCode);

        return healthWardPipeline.startPipeline(weeklyRecords).get(0);
    }

    @Override
    @Cacheable("namesAndGeoCodes")
    public Map<String, String> getHealthWardGeoCodesAndNames() {
        logger.debug("Fetching names and geocodes");
        Map<String, String> namesAndGeocodes = getAllHealthWards()
                .stream()
                .collect(Collectors.toMap(HealthWard::getName, HealthWard::getGeoCode,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return namesAndGeocodes;
    }

}
