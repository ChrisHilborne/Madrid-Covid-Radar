package com.chilborne.covidradar.services;

import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.exceptions.DataSaveException;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.repository.DailyRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class DailyRecordServiceImpl implements DailyRecordService {

    private final DailyRecordRepository dailyRecordRepository;
    private final CacheService cacheService;
    private final Logger logger = LoggerFactory.getLogger(DailyRecordServiceImpl.class);

    public DailyRecordServiceImpl(DailyRecordRepository dailyRecordRepository, CacheService cacheService) {
        this.dailyRecordRepository = dailyRecordRepository;
        this.cacheService = cacheService;
    }

    public DailyRecord save(DailyRecord dailyRecord) {
        dailyRecord.generateId();
        logger.debug("Saving dailyRecord id: " + dailyRecord.getId());
        DailyRecord saved = dailyRecordRepository.save(dailyRecord);
        if (saved.equals(dailyRecord)) {
            return saved;
        } else {
            throw new DataSaveException("Failed to save DailyRecord id: " + dailyRecord.getId());
        }
    }

    @Override
    public List<DailyRecord> getAll() {
        logger.debug("Fetching all DailyRecords...");
        List<DailyRecord> results = dailyRecordRepository.findAll();
        if (results.isEmpty()) {
            logger.error("No DailyRecords found.");
            throw new DataNotFoundException("No Daily Records found.");
        }
        return results;
    }

    @Override
    public List<DailyRecord> save(List<DailyRecord> dailyRecordList) {
        logger.debug("Saving dailyRecordList size: " + dailyRecordList.size());
        List<DailyRecord> savedList = new LinkedList<>();
        dailyRecordList.forEach(dailyRecord -> {

            try {
                DailyRecord saved = save(dailyRecord);
                savedList.add(saved);
            }
            catch (DataSaveException e) {
                logger.error(e.getMessage(), e);
                throw new DataSaveException("Error when saving dailyRecordList (hashcode " + dailyRecordList.hashCode() + ")");
            }
            finally {
                cacheService.clearCache();
            }
        });
        if (!savedList.equals(dailyRecordList)) {
            logger.error("dailyRecordList to save and returned list to not match.");
            throw new DataSaveException("Error when saving dailyRecordList (hashcode " + dailyRecordList.hashCode() + ")");
        }
        return savedList;
    }

    @Override
    public List<DailyRecord> getDailyRecordsByGeoCode(String geoCode) {
        logger.debug("Fetching DailyRecords for GeoCode: " + geoCode);
        List<DailyRecord> results = dailyRecordRepository.findByGeoCode(geoCode);
        if (results.isEmpty()) {
            logger.error("No data found for geocode: " + geoCode);
            throw new DataNotFoundException("No data found for geocode: " + geoCode);
        }
        return results;
    }

    @Override
    public DailyRecord getDailyRecordByGeoCodeAndDate(String geoCode, LocalDate date) {
        return dailyRecordRepository
                .findByGeoCodeAndDateReported(geoCode, date)
                .orElseThrow(() -> new DataNotFoundException("No data found for geocode: " + geoCode + " and dateReported: " + date.toString()));

    }

}
