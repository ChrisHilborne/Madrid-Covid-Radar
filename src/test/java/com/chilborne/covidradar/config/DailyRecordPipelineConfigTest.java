package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.DailyRecordParser;
import com.chilborne.covidradar.data.steps.DailyRecordSaver;
import com.chilborne.covidradar.data.steps.DailyRecordFixer;
import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DailyRecordPipelineConfigTest {

    @Mock
    DailyRecordParser parser;

    @Mock
    DailyRecordFixer trimmer;

    @Mock
    DailyRecordSaver saver;

    @InjectMocks
    DailyRecordPipelineConfig config;


    @Test
    void pipeline() {
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setGeoCode("01");
        List<DailyRecord> testList = List.of(test);

        //when
        Pipeline<String, List<DailyRecord>> configuredPipeline = config.pipeline();
        when(parser.process("test")).thenReturn(testList);
        when(trimmer.process(testList)).thenReturn(testList);
        when(saver.process(testList)).thenReturn(testList);

        List<DailyRecord> pipelineResult = configuredPipeline.execute("test");

        //verify
        verify(parser, times(1)).process(anyString());
        verify(parser, times(1)).process("test");
        verify(trimmer, times(1)).process(anyList());
        verify(trimmer, times(1)).process(testList);
        verify(saver, times(1)).process(anyList());
        verify(saver, times(1)).process(testList);

    }


}