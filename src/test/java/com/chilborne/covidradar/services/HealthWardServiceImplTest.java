package com.chilborne.covidradar.services;

import com.chilborne.covidradar.data.pipeline.DailyRecordsToHealthWardPipeline;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.HealthWard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthWardServiceImplTest {

    @Mock
    DailyRecordService dailyRecordService;

    @Mock
    DailyRecordsToHealthWardPipeline pipeline;

    @InjectMocks
    HealthWardServiceImpl healthWardService;

    LocalDate testDate = LocalDate.now();

    @Test
    void getAllHealthWards() {
        //given
        DailyRecord dailyRecordOne = new DailyRecord();
        dailyRecordOne.setHealthWard("one");
        dailyRecordOne.setDateReported(testDate);
        dailyRecordOne.setGeoCode("01");
        DailyRecord dailyRecordTwo = new DailyRecord();
        dailyRecordTwo.setHealthWard("two");
        dailyRecordTwo.setDateReported(testDate);
        dailyRecordTwo.setGeoCode("02");
        List<DailyRecord> dailyRecordList = List.of(dailyRecordOne, dailyRecordTwo);

        HealthWard healthWardOne = new HealthWard(List.of(dailyRecordOne));
        HealthWard healthWardTwo = new HealthWard(List.of(dailyRecordTwo));
        List<HealthWard> healthWardList = List.of(healthWardOne, healthWardTwo);

        //when
        when(dailyRecordService.getAll()).thenReturn(dailyRecordList);
        when(pipeline.startPipeline(List.of(dailyRecordOne, dailyRecordTwo))).thenReturn(healthWardList);
        List<HealthWard> returned = healthWardService.getAllHealthWards();

        //verify
        verify(dailyRecordService, times(1)).getAll();
        verify(pipeline, times(1)).startPipeline(anyList());
        verify(pipeline, times(1)).startPipeline(dailyRecordList);
        assertEquals(healthWardList, returned);

    }

    @Test
    void getHealthWardByGeoCode() {
        //given
        DailyRecord dailyRecordOne = new DailyRecord();
        dailyRecordOne.setHealthWard("one");
        dailyRecordOne.setDateReported(testDate);
        dailyRecordOne.setGeoCode("01");
        List<DailyRecord> dailyRecordList = List.of(dailyRecordOne);

        HealthWard healthWard = new HealthWard(dailyRecordList);

        //when
        when(dailyRecordService.getDailyRecordsByGeoCode("01")).thenReturn(dailyRecordList);
        when(pipeline.startPipeline(dailyRecordList)).thenReturn(List.of(healthWard));
        HealthWard result = healthWardService.getHealthWardByGeoCode("01");

        //verify
        verify(dailyRecordService, times(1)).getDailyRecordsByGeoCode(anyString());
        verify(dailyRecordService, times(1)).getDailyRecordsByGeoCode("01");
        verify(pipeline, times(1)).startPipeline(anyList());
        verify(pipeline, times(1)).startPipeline(dailyRecordList);
        assertEquals(healthWard, result);
    }

    @Test
    void getHealthWardGeoCodesAndNames() {
        //given
        HealthWard one = new HealthWard();
        one.setGeoCode("01");
        one.setName("one");
        HealthWard two = new HealthWard();
        two.setGeoCode("02");
        two.setName("two");
        List<HealthWard> healthWardList = List.of(one, two);

        Map<String, String> expectedResults = new HashMap<>();
        expectedResults.put("one", "01");
        expectedResults.put("two", "02");

        //when
        when(healthWardService.getAllHealthWards()).thenReturn(healthWardList);
        Map<String, String> results = healthWardService.getHealthWardGeoCodesAndNames();

        //verify
        assertEquals(expectedResults, results);
    }
}