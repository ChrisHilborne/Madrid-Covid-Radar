package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.DailyFigure;
import com.chilborne.covidradar.model.DailyFigureDTO;

import java.util.List;


public interface DailyFigureRepository {

    List<DailyFigureDTO> getDailyFiguresByGeoCode(String geoCode);

    void add(DailyFigure dailyFigure);
}
