package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.DailyFigure;
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
    public List<DailyFigure> getDailyFiguresByGeoCode(String geoCode) {
        List<DailyFigure> result = dailyFigures.stream()
                .filter( dailyFigure -> dailyFigure.getGeometricCode().equals(geoCode))
                .collect(Collectors.toUnmodifiableList());

        return result;
    }

    @Override
    public void add(DailyFigure dailyFigure) {
        this.dailyFigures.add(dailyFigure);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyFigureRepositoryTest that = (DailyFigureRepositoryTest) o;

        return dailyFigures.equals(that.dailyFigures);
    }

    @Override
    public int hashCode() {
        return dailyFigures.hashCode();
    }
}
