package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.DailyRecordConverter;
import com.chilborne.covidradar.data.steps.DailyRecordMapper;
import com.chilborne.covidradar.data.steps.DailyRecordSorter;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DailyRecordToHealthWardPipelineConfigTest {

    @Mock
    DailyRecordSorter sorter;

    @Mock
    DailyRecordMapper mapper;

    @Mock
    DailyRecordConverter converter;

    @InjectMocks
    DailyRecordToHealthWardPipelineConfig config;

    @Test
    void pipeline() {
        //given
        DailyRecord testDailyRecord = new DailyRecord();
        testDailyRecord.setHealthWard("test");
        testDailyRecord.setGeoCode("01");
        testDailyRecord.setDateReported(LocalDate.now());
        List<DailyRecord> testList = List.of(testDailyRecord);

        Map<String, List<DailyRecord>> testMap = new HashMap<>();
        testMap.put("test", testList);

        List<HealthWard> testHealthWard = List.of(new HealthWard(testList));

        //when
        Pipeline<List<DailyRecord>, List<HealthWard>> configuredPipeline = config.pipeline();
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