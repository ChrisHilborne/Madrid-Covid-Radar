package com.chilborne.covidradar.data.pipeline;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordDataPipelineTest {

    @Mock
    Pipeline pipeline;

    @InjectMocks
    WeeklyRecordInitalizePipeline weeklyRecordDataPipeline;

    @Captor
    ArgumentCaptor<String> argumentCaptor;

}