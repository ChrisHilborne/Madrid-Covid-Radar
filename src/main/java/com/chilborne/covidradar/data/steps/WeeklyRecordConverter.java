package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WeeklyRecordConverter implements Step<Map<String, List<WeeklyRecord>>, List<HealthWard>> {

    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordConverter.class);

    @Override
    public List<HealthWard> process(Map<String, List<WeeklyRecord>> input) {
        logger.debug("Converting Mapped WeeklyRecord objects to HealthWard objects...");
        List<HealthWard> HealthWard = input
                .values()
                .stream()
                .map(HealthWard::new)
                .collect(Collectors.toList());

        logger.debug("Finished converting WeeklyRecord data into " + HealthWard.size() + " HealthWard objects.");
        return HealthWard;
    }
}
