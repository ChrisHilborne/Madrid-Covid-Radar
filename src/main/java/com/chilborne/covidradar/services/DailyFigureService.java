package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DailyFigure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DailyFigureService {

    List<DailyFigure> getDailyFigures(String geoCode);

}
