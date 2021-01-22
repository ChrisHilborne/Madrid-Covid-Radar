package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.DistrictData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface DistrictDataRepository extends MongoRepository<DistrictData, String> {

    DistrictData save(DistrictData districtData);

    Optional<DistrictData> findByName(String name);

    Optional<DistrictData> findByGeoCode(String geoCode);



}
