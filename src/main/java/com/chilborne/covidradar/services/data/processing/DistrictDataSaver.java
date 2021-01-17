package com.chilborne.covidradar.services.data.processing;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.services.DistrictDataService;

import java.util.List;
import java.util.Map;

public class DistrictDataSaver implements DataSaver<Map<String, List<DailyRecord>>> {

    private final DistrictDataService districtDataService;

    public DistrictDataSaver(DistrictDataService districtDataService) {
        this.districtDataService = districtDataService;
    }

    @Override
    public void saveData(Map<String, List<DailyRecord>> data) {
        data.values()
                .stream()
                .map(DistrictData::new)
                .forEach(districtDataService::save);
    }
}
