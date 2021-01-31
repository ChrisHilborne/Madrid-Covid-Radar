package com.chilborne.covidradar.services;

import com.chilborne.covidradar.data.pipeline.DailyRecordsToDistrictDataPipeline;
import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DistrictDataServiceImpl implements DistrictDataService {

    private final DailyRecordService dailyRecordService;
    private final DailyRecordsToDistrictDataPipeline districtDataPipeline;
    private final Logger logger = LoggerFactory.getLogger(DistrictDataServiceImpl.class);

    public DistrictDataServiceImpl(DailyRecordService dailyRecordService,
                                   DailyRecordsToDistrictDataPipeline districtDataPipeline) {
        this.dailyRecordService = dailyRecordService;
        this.districtDataPipeline = districtDataPipeline;
    }

    @Override
    @Cacheable("districtData-all")
    public List<DistrictData> getAllDistrictData() throws DataNotFoundException {
        logger.debug("Fetching All District Data");
        List<DailyRecord> data = dailyRecordService.getAll();
        return districtDataPipeline.startPipeline(data);
    }

    @Override
    @Cacheable("districtData-district")
    public DistrictData getDistrictDataByName(String name) throws DataNotFoundException {
        logger.debug("Fetching Data for: " + name);
        List<DailyRecord> dailyRecords = dailyRecordService.getDailyRecordsByMunicipalDistrict(name);

        return new DistrictData(dailyRecords);
    }

    @Override
    @Cacheable("districtData")
    public DistrictData getDistrictDataByGeoCode(String geoCode) throws DataNotFoundException {
        logger.debug("Fetching data for GeoCode: " + geoCode);
        List<DailyRecord> dailyRecords = dailyRecordService.getDailyRecordsByGeoCode(geoCode);

        return new DistrictData(dailyRecords);
    }

    @Override
    @Cacheable("districtData-namesAndGeocodes")
    public Map<String, String> getDistrictGeoCodesAndNames() {
        Map<String, String> namesAndGeocodes = new HashMap<>();

        List<DistrictData> allData = getAllDistrictData();
        allData.forEach(districtData -> {
            namesAndGeocodes.put(districtData.getName(), districtData.getGeoCode());
        });

        return namesAndGeocodes;
    }




}
