package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeeklyRecordFixer implements Step<List<WeeklyRecord>, List<WeeklyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordFixer.class);

    @Override
    public List<WeeklyRecord> process(List<WeeklyRecord> data) {
        logger.debug("Trimming WeeklyRecord data...");
        logger.debug("Formatting GeoCodes...");
        logger.debug("Rounding infection rates to 2 decimal places...");

        data.forEach(weeklyRecordRecord ->
                {

                    //write lowercase name as geocode
                    String healthWard = weeklyRecordRecord.getHealthWard()
                            .toLowerCase()
                            .replace(' ', '_');
                    weeklyRecordRecord.setGeoCode(StringUtils.stripAccents(healthWard));

                    //round
                    int infectionRate2Weeks = (int) weeklyRecordRecord.getInfectionRateLastTwoWeeks();
                    weeklyRecordRecord.setInfectionRateLastTwoWeeks(infectionRate2Weeks);
                    if (weeklyRecordRecord.getInfectionRateLastTwoWeeks() < 0) {
                        throw new PipeLineProcessException("Two Week Infection Rate rounded to less than zero");
                    }

                    //round
                    int infectionRateTotal = (int) weeklyRecordRecord.getInfectionRateTotal();
                    weeklyRecordRecord.setInfectionRateTotal(infectionRateTotal);
                    if (weeklyRecordRecord.getInfectionRateTotal() < 0) {
                        throw new PipeLineProcessException("Total Infection Rate rounded to less than zero");
                    }

                });
        logger.debug("Finished trimming WeeklyRecord data.");
        return data;
    }
}
