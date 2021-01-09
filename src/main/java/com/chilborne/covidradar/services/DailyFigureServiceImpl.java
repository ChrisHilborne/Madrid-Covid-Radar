package com.chilborne.covidradar.services;


import com.chilborne.covidradar.model.DailyFigure;
import com.chilborne.covidradar.model.DailyFigureDTO;
import com.chilborne.covidradar.repository.DailyFigureRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Profile("test")
@Service
public class DailyFigureServiceImpl implements DailyFigureService {

    private final DailyFigureRepository dailyFigureRepository;

    public DailyFigureServiceImpl(DailyFigureRepository dailyFigureRepository) {
        this.dailyFigureRepository = dailyFigureRepository;
    }

    @Override
    public List<DailyFigureDTO> getDailyFigures(String geoCode) {
        return dailyFigureRepository.getDailyFiguresByGeoCode(geoCode);
    }

    @Override
    public void save(DailyFigure dailyFigure) {
        dailyFigureRepository.add(dailyFigure);
    }
}
