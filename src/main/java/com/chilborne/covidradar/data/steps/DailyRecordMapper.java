package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DailyRecordMapper implements Step<List<DailyRecord>, Map<String, List<DailyRecord>>> {

    Logger logger = LoggerFactory.getLogger(DailyRecordMapper.class);

    @Override
    public Map<String, List<DailyRecord>> process(List<DailyRecord> data) {
        logger.debug("Mapping DailyRecords By Health Ward");
        Map<String, List<DailyRecord>> dailyRecordsMappedByDistrict = new LinkedHashMap<>();

        data.forEach(
                dailyRecord ->
                {
                    if (dailyRecordsMappedByDistrict.containsKey(dailyRecord.getHealthWard())) {
                        dailyRecordsMappedByDistrict
                                .get(dailyRecord.getHealthWard())
                                .add(dailyRecord);
                    }
                    else {
                        List<DailyRecord> districtDailyResults = new LinkedList<>();
                        districtDailyResults.add(dailyRecord);
                        dailyRecordsMappedByDistrict.put(dailyRecord.getHealthWard(), districtDailyResults);
                    }
                });


        logger.debug("Results Mapped to " + dailyRecordsMappedByDistrict.size() + " Health Wards In Total");

        return dailyRecordsMappedByDistrict;
    }
}
