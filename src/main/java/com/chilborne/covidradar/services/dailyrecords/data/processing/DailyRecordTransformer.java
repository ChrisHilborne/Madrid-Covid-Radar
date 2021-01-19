package com.chilborne.covidradar.services.dailyrecords.data.processing;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces.DataTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DailyRecordTransformer implements DataTransformer {

    Logger logger = LoggerFactory.getLogger(DailyRecordTransformer.class);
    @Override
    public List<DistrictData> transform(Map<String, List<DailyRecord>> data) {
        logger.debug("Converting Mapped DailyRecord objects to DistrictData objects...");
        return data
                .values()
                .stream()
                .map(DistrictData::new)
                .collect(Collectors.toList());
    }
}
