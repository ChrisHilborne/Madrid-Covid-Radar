package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.model.HealthWard;
import com.chilborne.covidradar.services.WeeklyRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeeklyRecordSaver implements Step<List<WeeklyRecord>, List<WeeklyRecord>>{

    private final WeeklyRecordService weeklyRecordService;

    private final Logger logger = LoggerFactory.getLogger(HealthWard.class);

    @Autowired
    public WeeklyRecordSaver(WeeklyRecordService weeklyRecordService) {
        this.weeklyRecordService = weeklyRecordService;
    }


    @Override
    public List<WeeklyRecord> process(List<WeeklyRecord> input) throws PipeLineProcessException {
        logger.debug("Starting to save WeeklyRecords (hashcode: " + input.hashCode() + "...)");

        List<WeeklyRecord> output = weeklyRecordService.save(input);

        if (!output.equals(input)) {
            logger.error("WeeklyRecords not saved correctly.");
            throw new PipeLineProcessException("WeeklyRecords not saved correctly");
        }
        logger.debug("WeeklyRecords saved correctly.");
        return output;
    }
}
