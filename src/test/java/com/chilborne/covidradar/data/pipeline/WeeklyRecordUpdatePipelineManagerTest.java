package com.chilborne.covidradar.data.pipeline;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordUpdatePipelineManagerTest {

    @Mock
    Pipeline pipeline;

    @Captor
    ArgumentCaptor<HttpResponse<String>> httpResponseCaptor;

    @InjectMocks
    WeeklyRecordUpdatePipelineManager weeklyRecordUpdatePipelineManager;

    @Test
    void startPipeline() {
        //given
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        //when
        weeklyRecordUpdatePipelineManager.startPipeline(mockResponse);

        //verify
        verify(pipeline, times(1)).execute(any(HttpResponse.class));
        verify(pipeline).execute(httpResponseCaptor.capture());
        assertEquals(mockResponse, httpResponseCaptor.getValue());
    }
}