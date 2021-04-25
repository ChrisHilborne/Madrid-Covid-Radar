package com.chilborne.covidradar.services;

import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.exceptions.DataSaveException;
import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.repository.WeeklyRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class WeeklyRecordServiceImpl implements WeeklyRecordService {

    private final WeeklyRecordRepository weeklyRecordRepository;
    private final CacheService cacheService;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordServiceImpl.class);

    public WeeklyRecordServiceImpl(WeeklyRecordRepository weeklyRecordRepository, CacheService cacheService) {
        this.weeklyRecordRepository = weeklyRecordRepository;
        this.cacheService = cacheService;
    }

    public WeeklyRecord save(WeeklyRecord weeklyRecord) {
        weeklyRecord.generateId();
        logger.debug("Saving WeeklyRecord id: " + weeklyRecord.getId());
        WeeklyRecord saved = weeklyRecordRepository.save(weeklyRecord);
        if (saved.equals(weeklyRecord)) {
            return saved;
        } else {
            throw new DataSaveException("Failed to save WeeklyRecord id: " + weeklyRecord.getId());
        }
    }

    @Override
    public List<WeeklyRecord> getAll() {
        logger.debug("Fetching all WeeklyRecords...");
        List<WeeklyRecord> results = weeklyRecordRepository.findAll();
        if (results.isEmpty()) {
            logger.error("No WeeklyRecords found.");
            throw new DataNotFoundException("No Weekly Records found.");
        }
        return results;
    }

    @Override
    public List<WeeklyRecord> save(List<WeeklyRecord> weeklyRecordList) {
        logger.debug("Saving weeklyRecordList size: " + weeklyRecordList.size());
        List<WeeklyRecord> savedList = new LinkedList<>();
        weeklyRecordList.forEach(weeklyRecord -> {

            try {
                WeeklyRecord saved = save(weeklyRecord);
                savedList.add(saved);
            }
            catch (DataSaveException e) {
                logger.error(e.getMessage(), e);
                throw new DataSaveException("Error when saving weeklyRecordList (hashcode " + weeklyRecordList.hashCode() + ")");
            }
            finally {
                cacheService.clearCache();
            }
        });
        if (!savedList.equals(weeklyRecordList)) {
            logger.error("weeklyRecordList to save and returned list to not match.");
            throw new DataSaveException("Error when saving weeklyRecordList (hashcode " + weeklyRecordList.hashCode() + ")");
        }
        return savedList;
    }

    @Override
    public List<WeeklyRecord> getWeeklyRecordsByGeoCode(String geoCode) {
        logger.debug("Fetching WeeklyRecords for GeoCode: " + geoCode);
        List<WeeklyRecord> results = weeklyRecordRepository.findByGeoCode(geoCode);
        if (results.isEmpty()) {
            logger.error("No data found for geocode: " + geoCode);
            throw new DataNotFoundException("No data found for geocode: " + geoCode);
        }
        return results;
    }

    @Override
    public WeeklyRecord getWeeklyRecordByGeoCodeAndDate(String geoCode, LocalDate date) {
        return weeklyRecordRepository
                .findByGeoCodeAndDateReported(geoCode, date)
                .orElseThrow(() -> new DataNotFoundException("No data found for geocode: " + geoCode + " and dateReported: " + date.toString()));

    }

}
