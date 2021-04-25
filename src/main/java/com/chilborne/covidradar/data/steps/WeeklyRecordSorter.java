package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeeklyRecordSorter implements Step<List<WeeklyRecord>, List<WeeklyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordSorter.class);

    private final Comparator<WeeklyRecord> COMPARATOR = Comparator
                                                                .comparing(WeeklyRecord::getHealthWard)
                                                                .thenComparing(WeeklyRecord::getDateReported);

    @Override
    public List<WeeklyRecord> process(List<WeeklyRecord> data) {
        logger.debug("Sorting WeeklyRecord data...");



        List<WeeklyRecord> sortedData = data
                .stream()
                .sorted(COMPARATOR)
                .collect(Collectors.toList());

        logger.debug("Finished sorting WeeklyRecord data.");
        return sortedData;
    }
}
