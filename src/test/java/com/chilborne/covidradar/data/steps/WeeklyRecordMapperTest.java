package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeeklyRecordMapperTest {

    WeeklyRecordMapper weeklyRecordMapper = new WeeklyRecordMapper();

    @Test
    void process() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        one.setHealthWard("a");
        WeeklyRecord two = new WeeklyRecord();
        two.setHealthWard("b");
        List<WeeklyRecord> testData = List.of(one, two);


        Map<String, List<WeeklyRecord>> expectedResults = new LinkedHashMap<>();
        expectedResults.put("a", List.of(one));
        expectedResults.put("b", List.of(two));

        //when
        Map<String, List<WeeklyRecord>> mappedData = weeklyRecordMapper.process(testData);



        //verify
        assertEquals(expectedResults, mappedData);
    }



}