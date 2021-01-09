package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DailyFigureDTO;
import com.chilborne.covidradar.model.DistrictData;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Profile("test")
@Service
public class DistrictDataServiceImpl implements DistrictDataService {

    private final DailyFigureService dailyFigureService;

    public DistrictDataServiceImpl(DailyFigureService dailyFigureService) {
        this.dailyFigureService = dailyFigureService;
    }

    @Override
    @Cacheable("districtData")
    public DistrictData getDistrictData(String geoCode) {
        DistrictData result = new DistrictData();
        List<DailyFigureDTO> dailyFigures;
        String name;
        int totalCases;
        LocalDate lastUpdated;

        dailyFigures = dailyFigureService.getDailyFigures(geoCode);
        result.setDailyFigures(dailyFigures);

        name = dailyFigures.get(0).getDistrict();
        result.setName(name);

        totalCases = dailyFigures
                .get(dailyFigures.size() - 1)
                .getTotalCases();
        result.setTotalCases(totalCases);

        lastUpdated = dailyFigures
                .get(dailyFigures.size() - 1)
                .getDate();
        result.setLastUpdated(lastUpdated);

        return result;
    }
}
