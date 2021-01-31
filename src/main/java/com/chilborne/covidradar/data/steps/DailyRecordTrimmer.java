package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyRecordTrimmer implements Step<List<DailyRecord>, List<DailyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(DailyRecordTrimmer.class);

    @Override
    public List<DailyRecord> process(List<DailyRecord> data) {
        logger.debug("Trimming DailyRecord data...");
        logger.debug("Removing unnecessary characters from DistrictName...");
        logger.debug("Rounding infection rates to 2 decimal places...");
        List<DailyRecord> transformedData = data;
        transformedData
                .forEach(dailyRecord ->
                {
                    //round to 2dp
                    dailyRecord.setInfectionRateLastTwoWeeks(Math.round( dailyRecord.getInfectionRateLastTwoWeeks() * 100.0) / 100.0 );
                    if (dailyRecord.getInfectionRateLastTwoWeeks() < 0) {
                        throw new PipeLineProcessException("Two Week Infection Rate rounded to less than zero");
                    }
                    //round to 2dp
                    dailyRecord.setInfectionRateTotal(Math.round( dailyRecord.getInfectionRateTotal() * 100.0) / 100.0 );
                    if (dailyRecord.getInfectionRateTotal() < 0) {
                        throw new PipeLineProcessException("Total Infection Rate rounded to less than zero");
                    }

                });
        logger.debug("Finished trimming DailyRecord data.");
        return transformedData;
    }
}
