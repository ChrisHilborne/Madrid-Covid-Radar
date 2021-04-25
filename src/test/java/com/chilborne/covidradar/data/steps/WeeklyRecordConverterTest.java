package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


class WeeklyRecordConverterTest {


    WeeklyRecordConverter weeklyRecordConverter = new WeeklyRecordConverter();

    @Test
    void process() {
        //given
        Map<String, List<WeeklyRecord>> testMap = new HashMap<>();
        WeeklyRecord one = new WeeklyRecord();
        one.setHealthWard("one");
        one.setGeoCode("1");
        WeeklyRecord two = new WeeklyRecord();
        two.setHealthWard("two");
        two.setGeoCode("2");

        testMap.put("one", List.of(one));
        testMap.put("two", List.of(two));

        //when
        List<HealthWard> convertedTestData = weeklyRecordConverter.process(testMap);

        //verify
        assertAll("convertedData",
                () -> assertEquals(new HealthWard(List.of(one)), convertedTestData.get(0)),
                () -> assertEquals(new HealthWard(List.of(two)), convertedTestData.get(1))
        );

    }
}