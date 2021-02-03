package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.HealthWard;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface HealthWardService {

    HealthWard getHealthWardByName(String municipality) throws DataAccessException;

    List<HealthWard> getAllHealthWards();

    HealthWard getHealthWardByGeoCode(String geoCode);

    Map<String, String> getHealthWardGeoCodesAndNames();
}
