package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.WeeklyRecordConverter;
import com.chilborne.covidradar.data.steps.WeeklyRecordMapper;
import com.chilborne.covidradar.data.steps.WeeklyRecordSorter;
import com.chilborne.covidradar.model.WeeklyRecord;
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

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WeeklyRecordToHealthWardPipelineConfigTest {

    @Mock
    WeeklyRecordSorter sorter;

    @Mock
    WeeklyRecordMapper mapper;

    @Mock
    WeeklyRecordConverter converter;

    @InjectMocks
    WeeklyRecordToHealthWardPipelineConfig config;

    @Test
    void pipeline() {
        //given
        WeeklyRecord testWeeklyRecord = new WeeklyRecord();
        testWeeklyRecord.setHealthWard("test");
        testWeeklyRecord.setGeoCode("01");
        testWeeklyRecord.setDateReported(LocalDate.now());
        List<WeeklyRecord> testList = List.of(testWeeklyRecord);

        Map<String, List<WeeklyRecord>> testMap = new HashMap<>();
        testMap.put("test", testList);

        List<HealthWard> testHealthWard = List.of(new HealthWard(testList));

        //when
        Pipeline<List<WeeklyRecord>, List<HealthWard>> configuredPipeline = config.pipeline();
        when(sorter.process(testList)).thenReturn(testList);
        when(mapper.process(testList)).thenReturn(testMap);
        when(converter.process(testMap)).thenReturn(testHealthWard);

        List<HealthWard> pipelineResults = configuredPipeline.execute(testList);

        //verify
        verify(sorter, times(1)).process(anyList());
        verify(sorter, times(1)).process(testList);
        verify(mapper, times(1)).process(anyList());
        verify(mapper, times(1)).process(testList);
        verify(converter, times(1)).process(anyMap());
        verify(converter, times(1)).process(testMap);
    }
}