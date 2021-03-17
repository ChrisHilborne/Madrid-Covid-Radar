package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyRecordSorter implements Step<List<DailyRecord>, List<DailyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(DailyRecordSorter.class);

    private final Comparator<DailyRecord> COMPARATOR = Comparator
                                                                .comparing(DailyRecord::getHealthWard)
                                                                .thenComparing(DailyRecord::getDateReported);

    @Override
    public List<DailyRecord> process(List<DailyRecord> data) {
        logger.debug("Sorting DailyRecord data...");



        List<DailyRecord> sortedData = data
                .stream()
                .sorted(COMPARATOR)
                .collect(Collectors.toList());

        logger.debug("Finished sorting DailyRecord data.");
        return sortedData;
    }
}
