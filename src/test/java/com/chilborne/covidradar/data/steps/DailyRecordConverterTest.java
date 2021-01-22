package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class DailyRecordConverterTest {


    DailyRecordConverter dailyRecordConverter = new DailyRecordConverter();

    @Test
    void process() {
        //given
        Map<String, List<DailyRecord>> testMap = new HashMap<>();
        DailyRecord one = new DailyRecord();
        one.setMunicipalDistrict("one");
        one.setGeoCode("1");
        DailyRecord two = new DailyRecord();
        two.setMunicipalDistrict("two");
        two.setGeoCode("2");

        testMap.put("one", List.of(one));
        testMap.put("two", List.of(two));

        //when
        List<DistrictData> convertedTestData = dailyRecordConverter.process(testMap);

        //verify
        assertAll("convertedData",
                () -> assertEquals(new DistrictData(List.of(one)), convertedTestData.get(0)),
                () -> assertEquals(new DistrictData(List.of(two)), convertedTestData.get(1))
        );

    }
}