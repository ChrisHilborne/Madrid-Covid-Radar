package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DailyRecordFixer implements Step<List<DailyRecord>, List<DailyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(DailyRecordFixer.class);

    @Override
    public List<DailyRecord> process(List<DailyRecord> data) {
        logger.debug("Trimming DailyRecord data...");
        logger.debug("Removing unnecessary characters from DistrictName...");
        logger.debug("Rounding infection rates to 2 decimal places...");
        List<DailyRecord> fixedData = data;
        fixedData
                .forEach(dailyRecord ->
                {

                    //write lowercase name as geocode
                    String healthWard = dailyRecord.getHealthWard()
                            .toLowerCase()
                            .replace(' ', '_');
                    dailyRecord.setGeoCode(StringUtils.stripAccents(healthWard));

                    //round
                    int infectionRate2Weeks = (int) dailyRecord.getInfectionRateLastTwoWeeks();
                    dailyRecord.setInfectionRateLastTwoWeeks(infectionRate2Weeks);
                    if (dailyRecord.getInfectionRateLastTwoWeeks() < 0) {
                        throw new PipeLineProcessException("Two Week Infection Rate rounded to less than zero");
                    }

                    //round
                    int infectionRateTotal = (int) dailyRecord.getInfectionRateTotal();
                    dailyRecord.setInfectionRateTotal(infectionRateTotal);
                    if (dailyRecord.getInfectionRateTotal() < 0) {
                        throw new PipeLineProcessException("Total Infection Rate rounded to less than zero");
                    }

                });
        logger.debug("Finished trimming DailyRecord data.");
        return fixedData;
    }
}
