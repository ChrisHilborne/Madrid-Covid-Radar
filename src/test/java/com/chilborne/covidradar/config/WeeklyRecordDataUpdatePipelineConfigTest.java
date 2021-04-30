package com.chilborne.covidradar.config;

import com.chilborne.covidradar.data.pipeline.Pipeline;
import com.chilborne.covidradar.data.steps.HttpResponseValidator;
import com.chilborne.covidradar.data.steps.WeeklyRecordFixer;
import com.chilborne.covidradar.data.steps.WeeklyRecordParser;
import com.chilborne.covidradar.data.steps.WeeklyRecordSaver;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpResponse;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WeeklyRecordDataUpdatePipelineConfigTest {

    @Mock
    HttpResponseValidator validator;

    @Mock
    WeeklyRecordParser parser;

    @Mock
    WeeklyRecordFixer trimmer;

    @Mock
    WeeklyRecordSaver saver;

    @InjectMocks
    WeeklyUpdatePipelineConfig config;


    @Test
    void pipeline() {
        //given
        HttpResponse mockResponse = mock(HttpResponse.class);
        WeeklyRecord test = new WeeklyRecord();
        test.setHealthWard("test");
        test.setGeoCode("01");
        List<WeeklyRecord> testList = List.of(test);

        //when
        Pipeline<HttpResponse<String> , List<WeeklyRecord>> configuredPipeline = config.pipeline();
        when(validator.process(mockResponse)).thenReturn("test");
        when(parser.process("test")).thenReturn(testList);
        when(trimmer.process(testList)).thenReturn(testList);
        when(saver.process(testList)).thenReturn(testList);

        List<WeeklyRecord> pipelineResult = configuredPipeline.execute(mockResponse);

        //verify
        verify(validator, times(1)).process(any(HttpResponse.class));
        verify(validator, times(1)).process(mockResponse);
        verify(parser, times(1)).process(anyString());
        verify(parser, times(1)).process("test");
        verify(trimmer, times(1)).process(anyList());
        verify(trimmer, times(1)).process(testList);
        verify(saver, times(1)).process(anyList());
        verify(saver, times(1)).process(testList);

    }


}