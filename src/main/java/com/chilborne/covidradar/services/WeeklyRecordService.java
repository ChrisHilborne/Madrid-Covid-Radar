package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.WeeklyRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WeeklyRecordService {

    WeeklyRecord save(WeeklyRecord WeeklyRecord);

    List<WeeklyRecord> getAll();

    List<WeeklyRecord> save(List<WeeklyRecord> WeeklyRecordList);

    List<WeeklyRecord> getWeeklyRecordsByGeoCode(String geoCode);

    WeeklyRecord getWeeklyRecordByGeoCodeAndDate(String geoCode, LocalDate date);

    Map<String, String> getNamesAndGeoCodes();
}
