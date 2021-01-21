package com.chilborne.covidradar.data.processing.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DailyRecordSorter implements Step<List<DailyRecord>, List<DailyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(DailyRecordSorter.class);

    @Override
    public List<DailyRecord> process(List<DailyRecord> data) {
        logger.debug("Sorting DailyRecord data...");

        List<DailyRecord> sortedData = data;
        Collections.reverse(sortedData);

        logger.debug("Finished sorting DailyRecord data.");
        return sortedData;
    }
}
