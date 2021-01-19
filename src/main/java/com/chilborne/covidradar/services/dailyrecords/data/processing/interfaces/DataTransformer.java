package com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;

import java.util.List;
import java.util.Map;

public interface DataTransformer {

    List<DistrictData> transform(Map<String, List<DailyRecord>> data);
}
