package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WeeklyRecordMapper implements Step<List<WeeklyRecord>, Map<String, List<WeeklyRecord>>> {

    Logger logger = LoggerFactory.getLogger(WeeklyRecordMapper.class);

    @Override
    public Map<String, List<WeeklyRecord>> process(List<WeeklyRecord> data) {
        logger.debug("Mapping WeeklyRecords By HealthWard");
        Map<String, List<WeeklyRecord>> weeklyRecordsMappedByHealthWard = new LinkedHashMap<>();

        data.forEach(
                weeklyRecord ->
                {
                    if (weeklyRecordsMappedByHealthWard.containsKey(weeklyRecord.getHealthWard())) {
                        weeklyRecordsMappedByHealthWard
                                .get(weeklyRecord.getHealthWard())
                                .add(weeklyRecord);
                    }
                    else {
                        List<WeeklyRecord> districtWeeklyResults = new LinkedList<>();
                        districtWeeklyResults.add(weeklyRecord);
                        weeklyRecordsMappedByHealthWard.put(weeklyRecord.getHealthWard(), districtWeeklyResults);
                    }
                });


        logger.debug("Results Mapped to " + weeklyRecordsMappedByHealthWard.size() + " HealthWards In Total");

        return weeklyRecordsMappedByHealthWard;
    }
}
