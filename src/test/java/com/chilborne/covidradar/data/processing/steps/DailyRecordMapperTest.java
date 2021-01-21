package com.chilborne.covidradar.data.processing.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyRecordMapperTest {

    DailyRecordMapper dailyRecordMapper = new DailyRecordMapper();

    @Test
    void process() {
        //given
        DailyRecord one = new DailyRecord();
        one.setMunicipalDistrict("one");
        DailyRecord two = new DailyRecord();
        two.setMunicipalDistrict("two");
        List<DailyRecord> testData = List.of(one, two);

        //when
        Map<String, List<DailyRecord>> mappedData = dailyRecordMapper.process(testData);

        //verify
        assertAll("mapping",
                () -> assertEquals(2, mappedData.size()),
                () -> assertEquals(1, mappedData.get("one").size()),
                () -> assertEquals(List.of(one), mappedData.get("one")),
                () -> assertEquals(1, mappedData.get("two").size()),
                () -> assertEquals(List.of(two), mappedData.get("two"))
        );

    }
}