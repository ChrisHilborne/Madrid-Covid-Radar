package com.chilborne.covidradar.services;

import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.model.DistrictDataDTO;
import com.chilborne.covidradar.repository.DistrictDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class DistrictDataServiceImpl implements DistrictDataService {

    private final DistrictDataRepository districtDataRepository;
    private final Logger logger = LoggerFactory.getLogger(DistrictDataServiceImpl.class);

    public DistrictDataServiceImpl(DistrictDataRepository districtDataRepository) {
        this.districtDataRepository = districtDataRepository;
    }

    @Override
    @Cacheable("districtData-all")
    public List<DistrictDataDTO> getAllDistrictData() throws DataNotFoundException {
        logger.debug("Fetching All District Data");

        List<DistrictDataDTO> data = districtDataRepository.findAll()
                .stream()
                .map(DistrictDataDTO::new)
                .collect(Collectors.toUnmodifiableList());

        if (data.isEmpty()) {
            logger.error("No Data Found", new DataNotFoundException());
            throw new DataNotFoundException("No Data Found");
        } else {
            return data;
        }
    }

    @Override
    @Cacheable("districtData")
    public DistrictDataDTO getDistrictDataByName(String name) throws DataNotFoundException {
        logger.debug("Fetching Data for: " + name);
        DistrictData result = districtDataRepository.findByName(name)
                .orElseThrow(() -> new DataNotFoundException("District name: " + name + " not found."));

        return new DistrictDataDTO(result);
    }

    @Override
    @Cacheable ("districtData")
    public DistrictDataDTO getDistrictDataByGeoCode(String geoCode) throws DataNotFoundException {
        logger.debug("Fetching data for GeoCode: " + geoCode);
        DistrictData result = districtDataRepository.findByGeoCode(geoCode)
                .orElseThrow(() -> new DataNotFoundException("District geocode: " + geoCode + " not found."));

        return new DistrictDataDTO(result);
    }
    

    @Override
    @Cacheable("districtData-names")
    public List<String> getDistrictNames() {
        logger.debug("Fetching District Names");
        List<String> districtNames = districtDataRepository.findAll().stream()
                .map(DistrictData::getName)
                .collect(Collectors.toList());

        Collections.sort(districtNames);
        return districtNames;
    }

    @Override
    @Cacheable ("districtData-namesAndGeocodes")
    public Map<String, String> getDistrictGeoCodesAndNames() {
        Map<String, String> namesAndGeocodes = new HashMap<>();

        List<DistrictDataDTO> allData = getAllDistrictData();
        allData.forEach(districtData -> {
            namesAndGeocodes.put(districtData.getMunicipalDistrict(), districtData.getGeoCode());
        });

        return namesAndGeocodes;
    }

    @Override
    public DistrictData save(DistrictData districtData) {
        logger.debug("Saving: " + districtData.getName());
        return districtDataRepository.save(districtData);
    }

    @Override
    @CacheEvict(value = { "districtData",
            "districtData-all",
            "districtData-names",
            "districtData-namesAndGeocodes"},
            allEntries = true)
    public List<DistrictData> save(List<DistrictData> districtDataList) {
        List<DistrictData> savedDistrictDataList = new LinkedList<>();

        for (DistrictData districtData : districtDataList) {
            savedDistrictDataList.add(
                    save(districtData)
            );

        }
        return savedDistrictDataList;
    }


}
