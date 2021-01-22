package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DailyRecordMapper implements Step<List<DailyRecord>, Map<String, List<DailyRecord>>> {

    Logger logger = LoggerFactory.getLogger(DailyRecordMapper.class);

    @Override
    public Map<String, List<DailyRecord>> process(List<DailyRecord> data) {
        logger.debug("Mapping DailyRecords By District");
        HashMap<String, List<DailyRecord>> dailyRecordsMappedByDistrict = new HashMap<>();

        data.forEach(
                dailyRecord ->
                {
                    if (dailyRecordsMappedByDistrict.containsKey(dailyRecord.getMunicipalDistrict())) {
                        dailyRecordsMappedByDistrict
                                .get(dailyRecord.getMunicipalDistrict())
                                .add(dailyRecord);
                    }
                    else {
                        List<DailyRecord> districtDailyResults = new ArrayList<DailyRecord>();
                        districtDailyResults.add(dailyRecord);
                        dailyRecordsMappedByDistrict.put(dailyRecord.getMunicipalDistrict(), districtDailyResults);
                    }
                });

        if (dailyRecordsMappedByDistrict.size() < 21) {
            logger.error("Fewer Districts mapped than expected: "
                    + dailyRecordsMappedByDistrict.size() + " mapped, "
                    + "21 expected");
            throw new PipeLineProcessException("DailyRecordMapper mapped DailyRecords to fewer Districts than expected "
                    + dailyRecordsMappedByDistrict.size() + "actual, 21 expected.");
        }

        logger.debug("Results Mapped to " + dailyRecordsMappedByDistrict.size() + " Districts In Total");

        return dailyRecordsMappedByDistrict;
    }
}
