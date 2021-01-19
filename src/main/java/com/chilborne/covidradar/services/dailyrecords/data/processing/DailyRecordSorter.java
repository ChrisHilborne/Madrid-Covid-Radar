package com.chilborne.covidradar.services.dailyrecords.data.processing;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces.DataSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DailyRecordSorter implements DataSorter<List<DailyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(DailyRecordSorter.class);

    @Override
    public List<DailyRecord> sort(List<DailyRecord> data) {
        logger.debug("Sorting DailyRecord data...");

        List<DailyRecord> sortedData = data;
        Collections.reverse(sortedData);

        logger.debug("Finished sorting DailyRecord data.");
        return sortedData;
    }
}
