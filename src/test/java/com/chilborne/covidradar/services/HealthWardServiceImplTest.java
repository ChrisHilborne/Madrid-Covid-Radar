package com.chilborne.covidradar.services;

import com.chilborne.covidradar.data.pipeline.WeeklyRecordsToHealthWardPipelineManager;
import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthWardServiceImplTest {

    @Mock
    WeeklyRecordService weeklyRecordService;

    @Mock
    WeeklyRecordsToHealthWardPipelineManager pipeline;

    @InjectMocks
    HealthWardServiceImpl healthWardService;

    LocalDate testDate = LocalDate.now();

    @Test
    void getAllHealthWards() {
        //given
        WeeklyRecord weeklyRecordOne = new WeeklyRecord();
        weeklyRecordOne.setHealthWard("a");
        weeklyRecordOne.setDateReported(testDate);
        weeklyRecordOne.setGeoCode("01");
        WeeklyRecord weeklyRecordTwo = new WeeklyRecord();
        weeklyRecordTwo.setHealthWard("b");
        weeklyRecordTwo.setDateReported(testDate);
        weeklyRecordTwo.setGeoCode("02");
        List<WeeklyRecord> weeklyRecordList = List.of(weeklyRecordOne, weeklyRecordTwo);

        HealthWard healthWardOne = new HealthWard(List.of(weeklyRecordOne));
        HealthWard healthWardTwo = new HealthWard(List.of(weeklyRecordTwo));
        List<HealthWard> healthWardList = List.of(healthWardOne, healthWardTwo);

        //when
        when(weeklyRecordService.getAll()).thenReturn(weeklyRecordList);
        when(pipeline.startPipeline(List.of(weeklyRecordOne, weeklyRecordTwo))).thenReturn(healthWardList);
        List<HealthWard> returned = healthWardService.getAllHealthWards();

        //verify
        verify(weeklyRecordService, times(1)).getAll();
        verify(pipeline, times(1)).startPipeline(anyList());
        verify(pipeline, times(1)).startPipeline(weeklyRecordList);
        assertEquals(healthWardList, returned);

    }

    @Test
    void getHealthWardByGeoCode() {
        //given
        WeeklyRecord weeklyRecordOne = new WeeklyRecord();
        weeklyRecordOne.setHealthWard("one");
        weeklyRecordOne.setDateReported(testDate);
        weeklyRecordOne.setGeoCode("01");
        List<WeeklyRecord> weeklyRecordList = List.of(weeklyRecordOne);

        HealthWard healthWard = new HealthWard(weeklyRecordList);

        //when
        when(weeklyRecordService.getWeeklyRecordsByGeoCode("01")).thenReturn(weeklyRecordList);
        when(pipeline.startPipeline(weeklyRecordList)).thenReturn(List.of(healthWard));
        HealthWard result = healthWardService.getHealthWardByGeoCode("01");

        //verify
        verify(weeklyRecordService, times(1)).getWeeklyRecordsByGeoCode(anyString());
        verify(weeklyRecordService, times(1)).getWeeklyRecordsByGeoCode("01");
        verify(pipeline, times(1)).startPipeline(anyList());
        verify(pipeline, times(1)).startPipeline(weeklyRecordList);
        assertEquals(healthWard, result);
    }

    @Test
    void getHealthWardGeoCodesAndNames() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        one.setGeoCode("01");
        one.setHealthWard("b");
        WeeklyRecord two = new WeeklyRecord();
        two.setGeoCode("02");
        two.setHealthWard("a");
        WeeklyRecord three = new WeeklyRecord();
        three.setGeoCode("03");
        three.setHealthWard("c");
        List<WeeklyRecord> WeeklyRecordList = List.of(one, two, three);

        Map<String, String> expectedResults = new LinkedHashMap<>();
        expectedResults.put("b", "01");
        expectedResults.put("a", "02");
        expectedResults.put("c", "03");

        //when
        when(weeklyRecordService.getNamesAndGeoCodes()).thenReturn(expectedResults);
        Map<String, String> results = healthWardService.getHealthWardGeoCodesAndNames();

        //verify
        verify(weeklyRecordService, times(1)).getNamesAndGeoCodes();
        assertEquals(expectedResults, results);
    }
}