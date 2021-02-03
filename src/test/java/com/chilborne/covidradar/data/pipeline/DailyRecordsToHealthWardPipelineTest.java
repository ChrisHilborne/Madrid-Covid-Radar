package com.chilborne.covidradar.data.pipeline;

import com.chilborne.covidradar.model.DailyRecord;
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
class DailyRecordsToHealthWardPipelineTest {

    @Mock
    Pipeline pipeline;

    @InjectMocks
    DailyRecordsToHealthWardPipeline dailyRecordsToHealthWardPipeline;

    @Captor
    ArgumentCaptor<List<DailyRecord>> captor;

    @Test
    void startPipeline() {
        //given
        DailyRecord one = new DailyRecord();
        one.setHealthWard("one");
        one.setGeoCode("01");
        DailyRecord two = new DailyRecord();
        two.setHealthWard("two");
        two.setGeoCode("02");
        List<DailyRecord> toPipe = List.of(one, two);

        //when
        dailyRecordsToHealthWardPipeline.startPipeline(toPipe);

        //verify
        verify(pipeline, times(1)).execute(any());
        verify(pipeline, times(1)).execute(toPipe);
        verify(pipeline).execute(captor.capture());
        assertEquals(toPipe, captor.getValue());
    }
}