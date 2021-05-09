package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordsToHealthWardPipelineManagerTest {

    @Mock
    Pipeline pipeline;

    @InjectMocks
    WeeklyRecordsToHealthWardPipelineManager weeklyRecordsToHealthWardPipelineManager;

    @Captor
    ArgumentCaptor<List<WeeklyRecord>> captor;

    @Test
    void startPipeline() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        one.setHealthWard("one");
        one.setGeoCode("01");
        WeeklyRecord two = new WeeklyRecord();
        two.setHealthWard("two");
        two.setGeoCode("02");
        List<WeeklyRecord> toPipe = List.of(one, two);

        //when
        weeklyRecordsToHealthWardPipelineManager.startPipeline(toPipe);

        //verify
        verify(pipeline, times(1)).execute(any());
        verify(pipeline, times(1)).execute(toPipe);
        verify(pipeline).execute(captor.capture());
        assertEquals(toPipe, captor.getValue());
    }
}