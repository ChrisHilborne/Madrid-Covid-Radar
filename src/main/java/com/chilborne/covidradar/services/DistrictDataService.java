package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DistrictData;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface DistrictDataService {

    DistrictData getDistrictDataByName(String municipality) throws DataAccessException;

    List<DistrictData> getAllDistrictData();

    DistrictData getDistrictDataByGeoCode(String geoCode);

    Map<String, String> getDistrictGeoCodesAndNames();
}
