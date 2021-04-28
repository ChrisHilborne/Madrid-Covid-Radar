package com.chilborne.covidradar.data;

import com.chilborne.covidradar.data.collection.DataFetcher;
import com.chilborne.covidradar.data.collection.initialization.WeeklyRecordDataInitializer;
import com.chilborne.covidradar.data.pipeline.PipelineManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordDataInitializerTest {

    @Mock
    HttpRequest request;

    @Mock
    PipelineManager pipeline;

    @Mock
    DataFetcher dataFetcher;

    @InjectMocks
    WeeklyRecordDataInitializer weeklyRecordDataInitializer;

    @Test
    void initializeData() {
        //given
        String test = "test";
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        //when
        when(dataFetcher.fetchData(request)).thenReturn(mockResponse);
        when(mockResponse.body()).thenReturn(test);

        try {
            weeklyRecordDataInitializer.initializeData();
        } catch (Exception e) { }

        //verify
        verify(dataFetcher,times(1)).fetchData(any());
        verify(dataFetcher,times(1)).fetchData(request);
        verify(pipeline, times(1)).startPipeline(test);
    }

}