package com.chilborne.covidradar.data.pipeline;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordInitalizePipelineManagerTest {

    @Mock
    Pipeline pipeline;

    @Captor
    ArgumentCaptor<String> inputArgumentCaptor;

    @InjectMocks
    WeeklyRecordInitalizePipelineManager weeklyRecordInitalizePipelineManager;

    @Test
    void startPipeline() {
        //given
        String testInput = "This is input for a test";

        //when
        weeklyRecordInitalizePipelineManager.startPipeline(testInput);

        //verify
        verify(pipeline, times(1)).execute(testInput);
        verify(pipeline, times(1)).execute(anyString());
        verify(pipeline).execute(inputArgumentCaptor.capture());
        assertEquals(testInput, inputArgumentCaptor.getValue());
    }
}