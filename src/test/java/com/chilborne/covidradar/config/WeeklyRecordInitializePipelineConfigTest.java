package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.*;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordInitializePipelineConfigTest {

    @Mock
    WeeklyRecordParser parser;
    @Mock
    WeeklyRecordFilter filter;
    @Mock
    WeeklyRecordFixer trimmer;
    @Mock
    WeeklyRecordMapper mapper;
    @Mock
    WeeklyRecordAggregator aggregator;
    @Mock
    WeeklyRecordSaver saver;

    @InjectMocks
    WeeklyRecordInitializePipelineConfig config;

    @Test
    void pipeline() {
        //when
        Object pipeline = config.pipeline();

        //assert
        assertTrue(pipeline instanceof Pipeline);
    }

    @Test
    void pipeline_steps() {
        //given
        WeeklyRecord expected = new WeeklyRecord();
        expected.setHealthWard("expected");
        expected.setGeoCode("EXP");

        List<WeeklyRecord> expectedResults = List.of(expected);
        Map<String, List<WeeklyRecord>> intermediateMap = Map.of("test", expectedResults);

        //when
        when(parser.process("input")).thenReturn(expectedResults);
        when(filter.process(expectedResults)).thenReturn(expectedResults);
        when(trimmer.process(expectedResults)).thenReturn(expectedResults);
        when(mapper.process(expectedResults)).thenReturn(intermediateMap);
        when(aggregator.process(intermediateMap)).thenReturn(expectedResults);
        when(saver.process(expectedResults)).thenReturn(expectedResults);

        Pipeline<String, List<WeeklyRecord>> pipeline = config.pipeline();
        List<WeeklyRecord> actualResults = pipeline.execute("input");


        //assert
        assertAll("Pipeline steps",
                () -> verify(parser, times(1)).process(anyString()),
                () -> verify(trimmer,times(1)).process(anyList()),
                () -> verify(mapper, times(1)).process(anyList()),
                () -> verify(aggregator, times(1)).process(any(Map.class)),
                () -> verify(saver, times(1)).process(anyList())
        );
        assertEquals(expectedResults, actualResults);
    }
}