package com.chilborne.covidradar.data.collection.initialization;

import com.chilborne.covidradar.data.collection.DataFetcher;
import com.chilborne.covidradar.data.pipeline.PipelineManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordDataInitializerTest {

    @Mock
    DataFetcher fetcher;

    @Mock
    HttpRequest mockRequest;

    @Mock
    PipelineManager<String> pipelineManager;

    @Captor
    ArgumentCaptor<String> httpResponseBodyCaptor;

    @InjectMocks
    WeeklyRecordDataInitializer initializer;

    @Test
    void initializeData() {
        //given
        HttpResponse mockResponse = mock(HttpResponse.class);
        String mockResponseBody = "Mock Response Body";

        //when
        when(fetcher.fetchData(mockRequest)).thenReturn(mockResponse);
        when(mockResponse.body()).thenReturn(mockResponseBody);

        initializer.initializeData();

        //verify
        verify(fetcher, times(1)).fetchData(mockRequest);
        verify(fetcher, times(1)).fetchData(any(HttpRequest.class));
        verify(pipelineManager, times(1)).startPipeline(anyString());
        verify(pipelineManager).startPipeline(httpResponseBodyCaptor.capture());
        Assertions.assertEquals(mockResponseBody, httpResponseBodyCaptor.getValue());
    }

}