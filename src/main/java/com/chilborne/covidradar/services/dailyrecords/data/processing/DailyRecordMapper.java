package com.chilborne.covidradar.services.dailyrecords.data.processing;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces.DataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DailyRecordMapper implements DataMapper<List<DailyRecord>> {

    Logger logger = LoggerFactory.getLogger(DataMapper.class);

    @Override
    public Map<String, List<DailyRecord>> map(List<DailyRecord> data) {
        logger.debug("Mapping DailyRecords By District");
        HashMap<String, List<DailyRecord>> dailyRecordsMappedByDistrict = new HashMap<>();

        data
                .forEach(dailyRecord -> {
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

        logger.debug("Results Mapped to " + dailyRecordsMappedByDistrict.size() + " Districts In Total");

        return dailyRecordsMappedByDistrict;
    }
}
