package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.HealthWard;

import java.util.List;
import java.util.Map;

public interface HealthWardService {

    List<HealthWard> getAllHealthWards();

    HealthWard getHealthWardByGeoCode(String geoCode);

    Map<String, String> getHealthWardGeoCodesAndNames();
}
