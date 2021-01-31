package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DailyRecord;

import java.time.LocalDate;
import java.util.List;

public interface DailyRecordService  {

    DailyRecord save(DailyRecord dailyRecord);

    List<DailyRecord> getAll();

    List<DailyRecord> getDailyRecordsByMunicipalDistrict(String municipalDistrict);

    List<DailyRecord> save(List<DailyRecord> dailyRecordList);

    List<DailyRecord> getDailyRecordsByGeoCode(String geoCode);

    DailyRecord getDailyRecordByGeoCodeAndDate(String geoCode, LocalDate date);
}
