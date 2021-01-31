package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        one.setHealthWard("one");
        DailyRecord two = new DailyRecord();
        two.setHealthWard("two");
        List<DailyRecord> testData = new ArrayList<>();
        testData.add(one);
        testData.add(two);

        for (int i = 0; i < 19; i++) {
            DailyRecord dailyRecord = new DailyRecord();
            dailyRecord.setHealthWard("District no." + i);
            testData.add(dailyRecord);
        }

        //when
        Map<String, List<DailyRecord>> mappedData = dailyRecordMapper.process(testData);

        //verify
        assertAll("mapping",
                () -> assertEquals(21, mappedData.size()),
                () -> assertEquals(1, mappedData.get("one").size()),
                () -> assertEquals(List.of(one), mappedData.get("one")),
                () -> assertEquals(1, mappedData.get("two").size()),
                () -> assertEquals(List.of(two), mappedData.get("two"))
        );
    }



}