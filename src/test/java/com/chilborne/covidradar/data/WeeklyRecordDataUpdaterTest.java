package com.chilborne.covidradar.data;

import com.chilborne.covidradar.data.collection.DataFetcher;
import com.chilborne.covidradar.data.collection.update.WeeklyRecordDataUpdater;
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
class WeeklyRecordDataUpdaterTest {

    @Mock
    DataFetcher dataFetcher;

    @Mock
    HttpRequest mockRequest;

    @Mock
    PipelineManager pipelineManager;

    @InjectMocks
    WeeklyRecordDataUpdater weeklyRecordDataUpdater;

    @Test
    void updateData() {
        //given
        HttpResponse response = mock(HttpResponse.class);

        //when
        when(dataFetcher.fetchData(mockRequest)).thenReturn(response);
        weeklyRecordDataUpdater.updateData();

        //verify
        verify(dataFetcher, times(1)).fetchData(mockRequest);
        verify(dataFetcher, times(1)).fetchData(any());
        verify(pipelineManager, times(1)).startPipeline(response);
        verify(pipelineManager,times(1)).startPipeline(any());
    }
}