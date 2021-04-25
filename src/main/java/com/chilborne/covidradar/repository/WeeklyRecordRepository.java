package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeeklyRecordRepository extends MongoRepository<WeeklyRecord, String> {

    WeeklyRecord save(WeeklyRecord weeklyRecord);

    List<WeeklyRecord> findAll();

    List<WeeklyRecord> findByGeoCode(String geoCode);

    List<WeeklyRecord> findByDateReported(LocalDate date);

    Optional<WeeklyRecord> findByGeoCodeAndDateReported(String geoCode, LocalDate dateReported);

    Optional<WeeklyRecord> findByHealthWardAndDateReported(String healthWard, LocalDate dateReported);
}
