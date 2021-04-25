package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.HealthWard;
import com.chilborne.covidradar.services.WeeklyRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyRecordSaver implements Step<List<DailyRecord>, List<DailyRecord>>{

    private final WeeklyRecordService weeklyRecordService;

    private final Logger logger = LoggerFactory.getLogger(HealthWard.class);

    @Autowired
    public DailyRecordSaver(WeeklyRecordService weeklyRecordService) {
        this.weeklyRecordService = weeklyRecordService;
    }


    @Override
    public List<DailyRecord> process(List<DailyRecord> input) throws PipeLineProcessException {
        logger.debug("Starting to save DailyRecords (hashcode: " + input.hashCode() + "...)");

        List<DailyRecord> output = weeklyRecordService.save(input);

        if (!output.equals(input)) {
            logger.error("DailyRecords not saved correctly.");
            throw new PipeLineProcessException("DailyRecords not saved correctly");
        }
        logger.debug("DailyRecords saved correctly.");
        return output;
    }
}
