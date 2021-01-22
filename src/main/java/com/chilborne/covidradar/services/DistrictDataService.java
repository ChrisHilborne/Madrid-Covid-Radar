package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.model.DistrictDataDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DistrictDataService {

    DistrictDataDTO getDistrictDataByName(String municipality) throws DataAccessException;

    List<DistrictDataDTO> getAllDistrictData();

    List<String> getDistrictNames();

    DistrictData save(DistrictData districtData);

    List<DistrictData> save(List<DistrictData> districtDataList);

    DistrictDataDTO getDistrictDataByGeoCode(String geoCode);
}
