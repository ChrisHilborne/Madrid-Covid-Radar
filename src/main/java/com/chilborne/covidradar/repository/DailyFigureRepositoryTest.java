package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.DailyFigure;
import com.chilborne.covidradar.model.DailyFigureDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Profile("test")
@Repository
public class DailyFigureRepositoryTest implements DailyFigureRepository {

    private List<DailyFigure> dailyFigures = new ArrayList<>();

    public DailyFigureRepositoryTest() {
    }

    @Override
    public List<DailyFigureDTO> getDailyFiguresByGeoCode(String geoCode) {
        List<DailyFigureDTO> dailyFigureData = dailyFigures.stream()
                .filter(dailyFigure -> dailyFigure.getGeoCode().equals(geoCode))
                .map(DailyFigureDTO::new)
                .collect(Collectors.toUnmodifiableList());

        return dailyFigureData;
    }

    @Override
    public void add(DailyFigure dailyFigure) {
        this.dailyFigures.add(dailyFigure);
    }


}
