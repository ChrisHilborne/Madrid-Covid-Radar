package com.chilborne.covidradar.services;


import com.chilborne.covidradar.model.DailyFigure;
import com.chilborne.covidradar.repository.DailyFigureRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
@Profile("test")
@Service
public class DailyFigureServiceTest implements DailyFigureService {

    private final DailyFigureRepository dailyFigureRepository;

    public DailyFigureServiceTest(DailyFigureRepository dailyFigureRepository) {
        this.dailyFigureRepository = dailyFigureRepository;
    }

    @Override
    public List<DailyFigure> getDailyFigures(String geoCode) {
        return dailyFigureRepository.getDailyFiguresByGeoCode(geoCode);
    }
}
