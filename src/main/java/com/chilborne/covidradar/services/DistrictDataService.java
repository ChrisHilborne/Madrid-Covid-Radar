package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.model.DistrictDataDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DistrictDataService {

    DistrictDataDTO getDistrictData(String municipality) throws DataAccessException;

    List<DistrictDataDTO> getAllDistrictData();

    List<String> getDistrictNames();

    void save(DistrictData districtData);
}
