package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.WeeklyRecord;

import java.time.LocalDate;
import java.util.List;

public interface WeeklyRecordService {

    WeeklyRecord save(WeeklyRecord weeklyRecord);

    List<WeeklyRecord> getAll();

    List<WeeklyRecord> save(List<WeeklyRecord> weeklyRecordList);

    List<WeeklyRecord> getWeeklyRecordsByGeoCode(String geoCode);

    WeeklyRecord getWeeklyRecordByGeoCodeAndDate(String geoCode, LocalDate date);
}
