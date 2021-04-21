package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.DailyRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyRecordRepository extends MongoRepository<DailyRecord, String> {

    DailyRecord save(DailyRecord dailyRecord);

    List<DailyRecord> findAll();

    List<DailyRecord> findByGeoCode(String geoCode);

    List<DailyRecord> findByDateReported(LocalDate dateReported);

    Optional<DailyRecord> findByGeoCodeAndDateReported(String geoCode, LocalDate dateReported);

    Optional<DailyRecord> findByHealthWardAndDateReported(String healthWard, LocalDate dateReported);
}
