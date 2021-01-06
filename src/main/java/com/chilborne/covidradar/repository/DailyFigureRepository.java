package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.DailyFigure;

import java.util.List;


public interface DailyFigureRepository {

    List<DailyFigure> getDailyFiguresByGeoCode(String geoCode);

    void add(DailyFigure dailyFigure);
}
