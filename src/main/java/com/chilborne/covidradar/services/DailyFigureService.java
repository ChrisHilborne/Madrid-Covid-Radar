package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DailyFigure;
import com.chilborne.covidradar.model.DailyFigureDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DailyFigureService {

    List<DailyFigureDTO> getDailyFigures(String geoCode);

    void save(DailyFigure dailyFigure);
}
