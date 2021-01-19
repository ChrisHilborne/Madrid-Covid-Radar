package com.chilborne.covidradar.services.districtdata;

import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.model.DistrictDataDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DistrictDataService {

    DistrictDataDTO getDistrictDataByName(String municipality) throws DataAccessException;

    List<DistrictDataDTO> getAllDistrictData();

    List<String> getDistrictNames();

    void save(DistrictData districtData);

    DistrictDataDTO getDistrictDataByGeoCode(String geoCode);

    void save(List<DistrictData> mappedData);
}
