package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.model.DistrictDataDTO;
import com.chilborne.covidradar.repository.DistrictDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DistrictDataServiceImpl implements DistrictDataService {

    private final DistrictDataRepository districtDataRepository;
    private final Logger logger = LoggerFactory.getLogger(DistrictDataServiceImpl.class);

    public DistrictDataServiceImpl(DistrictDataRepository districtDataRepository) {
        this.districtDataRepository = districtDataRepository;
    }

    @Override
    @Cacheable ("districtData")
    public DistrictDataDTO getDistrictDataByGeoCode(String geoCode) {
        logger.debug("Fetching data for GeoCode: " + geoCode);
        DistrictData result = districtDataRepository.findByGeoCode(geoCode).orElseThrow(RuntimeException::new);

        return new DistrictDataDTO(result);
    }

    @Override
    @Cacheable("districtData")
    public DistrictDataDTO getDistrictDataByName(String name) throws RuntimeException {
        logger.debug("Fetching Data for: " + name);
        DistrictData result = districtDataRepository.findByName(name).orElseThrow(RuntimeException::new);
        //TODO write DistrictNotFoundException
        return new DistrictDataDTO(result);
    }

    @Override
    public List<DistrictDataDTO> getAllDistrictData() {
        logger.debug("Fetching All District Data");

        List<DistrictDataDTO> data = districtDataRepository.findAll()
                .stream()
                .map(DistrictDataDTO::new)
                .collect(Collectors.toUnmodifiableList());

        if (data.isEmpty()) {
            logger.error("No Data Found");
            return List.of();
        }
        else {
            return data;
        }
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
    @CacheEvict(value = "districtData", allEntries = true)
    public void save(DistrictData districtData) {
        logger.debug("Saving: " + districtData.getName());
        districtDataRepository.save(districtData);
    }

}
