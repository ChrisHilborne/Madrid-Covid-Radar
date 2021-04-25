package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.events.InitialDataEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordDataPipelineTest {

    @Mock
    Pipeline pipeline;

    @InjectMocks
    WeeklyRecordInitalizePipeline weeklyRecordDataPipeline;

    @Captor
    ArgumentCaptor<String> argumentCaptor;

    @Test
    void startPipeline() {
        //given
        String testInput = "test";
        InitialDataEvent newDataEvent = new InitialDataEvent(this, testInput);

        //when
        weeklyRecordDataPipeline.startInitializePipeline(newDataEvent);

        //verify
        verify(pipeline, times(1)).execute(any());
        verify(pipeline, times(1)).execute(testInput);
        verify(pipeline).execute(argumentCaptor.capture());
        String capturedInput = argumentCaptor.getValue();
        assertEquals(testInput, capturedInput);
    }
}