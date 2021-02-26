package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.events.UpdatedDataEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DailyRecordDataPipelineTest {

    @Mock
    Pipeline pipeline;

    @InjectMocks
    DailyRecordProcessingPipeline dailyRecordDataPipeline;

    @Captor
    ArgumentCaptor<String> argumentCaptor;

    @Test
    void startPipeline() {
        //given
        String testInput = "test";
        UpdatedDataEvent newDataEvent = new UpdatedDataEvent(this, testInput);

        //when
        dailyRecordDataPipeline.startPipeline(newDataEvent);

        //verify
        verify(pipeline, times(1)).execute(any());
        verify(pipeline, times(1)).execute(testInput);
        verify(pipeline).execute(argumentCaptor.capture());
        String capturedInput = argumentCaptor.getValue();
        assertEquals(testInput, capturedInput);
    }
}