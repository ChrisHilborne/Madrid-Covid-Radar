package com.chilborne.covidradar.data.collection.update;

import com.chilborne.covidradar.data.collection.DataFetcher;
import com.chilborne.covidradar.data.pipeline.PipelineManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordDataUpdaterTest {

    @Mock
    DataFetcher fetcher;

    @Mock
    HttpRequest mockRequest;

    @Mock
    PipelineManager<HttpResponse> pipelineManager;

    @Captor
    ArgumentCaptor<HttpResponse> httpResponseCaptor;

    @InjectMocks
    WeeklyRecordDataUpdater updater;

    @Test
    void updateData() {
        //given
        HttpResponse mockResponse = mock(HttpResponse.class);

        //when
        when(fetcher.fetchData(mockRequest)).thenReturn(mockResponse);
        updater.updateData();

        //verify
        verify(fetcher, times(1)).fetchData(mockRequest);
        verify(fetcher, times(1)).fetchData(any(HttpRequest.class));
        verify(pipelineManager, times(1)).startPipeline(any(HttpResponse.class));
        verify(pipelineManager).startPipeline(httpResponseCaptor.capture());
        assertEquals(mockResponse, httpResponseCaptor.getValue());
    }
}