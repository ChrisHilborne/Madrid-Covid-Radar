package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DistrictData;

public interface DistrictDataService {

    DistrictData getDistrictData(String geoCode);

}
