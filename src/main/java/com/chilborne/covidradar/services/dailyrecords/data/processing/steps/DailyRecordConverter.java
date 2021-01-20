package com.chilborne.covidradar.services.dailyrecords.data.processing.steps;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DailyRecordConverter implements Step<Map<String, List<DailyRecord>>, List<DistrictData>> {

    Logger logger = LoggerFactory.getLogger(DailyRecordConverter.class);

    @Override
    public List<DistrictData> process(Map<String, List<DailyRecord>> input) {
        logger.debug("Converting Mapped DailyRecord objects to DistrictData objects...");
        List<DistrictData> districtData = input
                .values()
                .stream()
                .map(DistrictData::new)
                .collect(Collectors.toList());

        logger.debug("Finished converting DailyRecord data into " + districtData.size() + "districtData objects.");
        return districtData;
    }
}
