package com.chilborne.covidradar.services;

import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.repository.DailyRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyRecordServiceImpl implements DailyRecordService {

    private final DailyRecordRepository dailyRecordRepository;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordServiceImpl.class);

    public DailyRecordServiceImpl(DailyRecordRepository dailyRecordRepository) {
        this.dailyRecordRepository = dailyRecordRepository;
    }

    @Override
    public DailyRecord save(DailyRecord dailyRecord) {
        logger.debug("Saving dailyRecord id: " + dailyRecord.getId());
        dailyRecord.generateId();
        return dailyRecordRepository.save(dailyRecord);
    }

    @Override
    public List<DailyRecord> getAll() {
        logger.debug("Fetching All DailyRecords...");
        List<DailyRecord> results = dailyRecordRepository.findAll();
        if (results.isEmpty()) {
            logger.error("No DailyRecords found.");
            throw new DataNotFoundException("No data found.");
        }
        return results;
    }

    @Override
    public List<DailyRecord> getDailyRecordsByHealthWard(String healthWard) {
        logger.debug("Fetching DailyRecords for health ward: " + healthWard);
        List<DailyRecord> results = dailyRecordRepository.findByHealthWard(healthWard);
        if (results.isEmpty()) {
            logger.error("No data for health ward " + healthWard + " found");
            throw new DataNotFoundException("No data for health ward " + healthWard);
        }
        return results;
    }

    @Override
    @CacheEvict( {
            "healthWard-all",
            "healthWard-name",
            "healthWard-geoCode",
            "namesAndGeoCodes"
    })
    public List<DailyRecord> save(List<DailyRecord> dailyRecordList) {
        logger.debug("Saving dailyRecordList (hashcode: " + dailyRecordList.hashCode() +")");
        List<DailyRecord> savedList = new ArrayList<>();
        dailyRecordList.forEach(dailyRecord -> {
            dailyRecord.generateId();
            logger.debug("Saving DailyRecord id: " + dailyRecord.getId());
            savedList.add(dailyRecordRepository.save(dailyRecord));
        });
        if (!savedList.equals(dailyRecordList)) {
            logger.error("dailyRecordList to save and returned list to not match.");
            throw new RuntimeException("Error when saving dailyRecordList.");
        }
        return savedList;

    }

    @Override
    public List<DailyRecord> getDailyRecordsByGeoCode(String geoCode) {
        logger.debug("Fetching DailyRecords for GeoCode: " + geoCode);
        List<DailyRecord> results = dailyRecordRepository.findByGeoCode(geoCode);
        if (results.isEmpty()) {
            logger.error("Results are empty for GeoCode: " + geoCode);
        }
        return results;
    }

    @Override
    public DailyRecord getDailyRecordByGeoCodeAndDate(String geoCode, LocalDate date) {
        return dailyRecordRepository
                .findByGeoCodeAndDateReported(geoCode, date)
                .orElseThrow(() -> new DataNotFoundException("No Data Found for GeoCode " + geoCode + " and date" + date.toString()));

    }

}
