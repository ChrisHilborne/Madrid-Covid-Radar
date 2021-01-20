package com.chilborne.covidradar.services.dailyrecords.data.processing.steps;

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
                    dailyRecord
                            .setMunicipalDistrict(dailyRecord.getMunicipalDistrict()
                                                .substring(7)
                                                .toLowerCase()
                            );

                    dailyRecord.setInfectionRateLastTwoWeeks(Math.round( dailyRecord.getInfectionRateLastTwoWeeks() * 100.0) / 100.0 );
                    dailyRecord.setInfectionRateTotal(Math.round( dailyRecord.getInfectionRateTotal() * 100.0) / 100.0 );
                });
        logger.debug("Finished trimming DailyRecord data.");
        return transformedData;
    }
}
