package com.chilborne.covidradar.services;

import com.chilborne.covidradar.cache.CacheService;
import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.exceptions.DataSaveException;
import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.repository.WeeklyRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeeklyRecordServiceImpl implements WeeklyRecordService {

    private final WeeklyRecordRepository weeklyRecordRepository;
    private final CacheService cacheService;
    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordServiceImpl.class);

    public WeeklyRecordServiceImpl(WeeklyRecordRepository weeklyRecordRepository, CacheService cacheService) {
        this.weeklyRecordRepository = weeklyRecordRepository;
        this.cacheService = cacheService;
    }

    public WeeklyRecord save(WeeklyRecord WeeklyRecord) {
        WeeklyRecord.generateId();
        logger.debug("Saving WeeklyRecord id: " + WeeklyRecord.getId());
        WeeklyRecord saved = weeklyRecordRepository.save(WeeklyRecord);
        if (saved.equals(WeeklyRecord)) {
            return saved;
        } else {
            throw new DataSaveException("Failed to save WeeklyRecord id: " + WeeklyRecord.getId());
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
    public List<WeeklyRecord> save(List<WeeklyRecord> WeeklyRecordList) {
        logger.debug("Saving WeeklyRecordList size: " + WeeklyRecordList.size());
        cacheService.clearCache();
        List<WeeklyRecord> savedList = new LinkedList<>();
        WeeklyRecordList.forEach(WeeklyRecord -> {

            try {
                WeeklyRecord saved = save(WeeklyRecord);
                savedList.add(saved);
            }
            catch (DataSaveException e) {
                logger.error(e.getMessage(), e);
                throw new DataSaveException("Error when saving WeeklyRecordList (hashcode " + WeeklyRecordList.hashCode() + ")");
            }
        });
        if (!savedList.equals(WeeklyRecordList)) {
            logger.error("WeeklyRecordList to save and returned list to not match.");
            throw new DataSaveException("Error when saving WeeklyRecordList (hashcode " + WeeklyRecordList.hashCode() + ")");
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

    @Override
    public Map<String, String> getNamesAndGeoCodes() {
        Map<String, String> namesAndGeocodes = new LinkedHashMap<>();

        LocalDate firstDateOfResults = LocalDate.of(2020, 3, 3);
        List<WeeklyRecord> firstDateData = weeklyRecordRepository.findByDateReported(firstDateOfResults);

         namesAndGeocodes = firstDateData.stream()
                .collect(Collectors.toMap(WeeklyRecord::getHealthWard, WeeklyRecord::getGeoCode,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return namesAndGeocodes;

    }


}
