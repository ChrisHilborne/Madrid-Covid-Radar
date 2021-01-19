package com.chilborne.covidradar.services.dailyrecords.data.processing;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces.DataFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyRecordFilter implements DataFilter<List<DailyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(DailyRecordFilter.class);

    @Override
    public List<DailyRecord> filter(List<DailyRecord> data) {
        logger.debug("Filtering DailyRecord Data");
        List<DailyRecord> filteredData = data.stream()
                .filter(dailyRecord -> dailyRecord.getMunicipalDistrict().matches("Madrid.*"))
                .collect(Collectors.toList());
        logger.debug("New Number of Records: " + filteredData.size());
        return filteredData;
    }
}
