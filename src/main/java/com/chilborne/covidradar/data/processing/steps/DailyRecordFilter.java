package com.chilborne.covidradar.data.processing.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyRecordFilter implements Step<List<DailyRecord>, List<DailyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(DailyRecordFilter.class);

    @Override
    public List<DailyRecord> process(List<DailyRecord> data) {
        logger.debug("Filtering DailyRecord Data");
        List<DailyRecord> filteredData = data.stream()
                .filter(dailyRecord -> dailyRecord.getMunicipalDistrict().matches("Madrid.*"))
                .collect(Collectors.toList());

        if (filteredData.size() == 0) {
            logger.error("No Records After Filtering");
            throw new PipeLineProcessException("DailyRecordFilter removed all records");
        }

        logger.debug("New Number of Records: " + filteredData.size());
        return filteredData;
    }
}
