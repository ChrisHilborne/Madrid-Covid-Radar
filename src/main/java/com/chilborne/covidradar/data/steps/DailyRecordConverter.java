package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DailyRecordConverter implements Step<Map<String, List<DailyRecord>>, List<HealthWard>> {

    Logger logger = LoggerFactory.getLogger(DailyRecordConverter.class);

    @Override
    public List<HealthWard> process(Map<String, List<DailyRecord>> input) {
        logger.debug("Converting Mapped DailyRecord objects to DistrictData objects...");
        List<HealthWard> districtData = input
                .values()
                .stream()
                .map(HealthWard::new)
                .collect(Collectors.toList());

        logger.debug("Finished converting DailyRecord data into " + districtData.size() + "districtData objects.");
        return districtData;
    }
}
