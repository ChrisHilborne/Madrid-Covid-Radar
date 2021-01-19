package com.chilborne.covidradar.services.dailyrecords.data.processing;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces.DataTrimmer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyRecordTrimmer implements DataTrimmer<List<DailyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(DailyRecordTrimmer.class);

    @Override
    public List<DailyRecord> trim(List<DailyRecord> data) {
        logger.debug("Trimming DailyRecord data...");

        List<DailyRecord> transformedData = data;
        transformedData.forEach(dailyRecord ->
        {
            logger.debug("Removing unnecessary characters from DistrictName...");
            dailyRecord.setMunicipalDistrict(dailyRecord.getMunicipalDistrict()
                    .substring(7)
                    .toLowerCase());
            logger.debug("Rounding infection rates to 2 decimal places...");
            dailyRecord.setInfectionRateLastTwoWeeks(Math.round( dailyRecord.getInfectionRateLastTwoWeeks() * 100.0) / 100.0 );
            dailyRecord.setInfectionRateTotal(Math.round( dailyRecord.getInfectionRateTotal() * 100.0) / 100.0 );
        });
        logger.debug("Finished trimming DailyRecord data.");
        return transformedData;
    }
}
