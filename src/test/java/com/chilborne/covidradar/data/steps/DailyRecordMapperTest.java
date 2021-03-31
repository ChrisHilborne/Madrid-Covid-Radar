package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyRecordMapperTest {

    DailyRecordMapper dailyRecordMapper = new DailyRecordMapper();

    @Test
    void process() {
        //given
        DailyRecord one = new DailyRecord();
        one.setHealthWard("a");
        DailyRecord two = new DailyRecord();
        two.setHealthWard("b");
        List<DailyRecord> testData = List.of(one, two);


        Map<String, List<DailyRecord>> expectedResults = new LinkedHashMap<>();
        expectedResults.put("a", List.of(one));
        expectedResults.put("b", List.of(two));

        //when
        Map<String, List<DailyRecord>> mappedData = dailyRecordMapper.process(testData);



        //verify
        assertEquals(expectedResults, mappedData);
    }



}