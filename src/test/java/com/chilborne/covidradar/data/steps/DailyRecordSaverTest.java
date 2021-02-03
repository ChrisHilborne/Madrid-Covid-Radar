package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.services.DailyRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyRecordSaverTest {

    @Mock
    DailyRecordService service;

    @InjectMocks
    DailyRecordSaver saver;

    @Captor
    ArgumentCaptor<List<DailyRecord>> dailyRecordListCaptor;

    @Test
    void process_Success() {
        //given
        DailyRecord one = new DailyRecord();
        one.setGeoCode("01");
        one.setHealthWard("one");
        List<DailyRecord> toSave = List.of(one);

        //when
        when(service.save(toSave)).thenReturn(List.of(one));
        List<DailyRecord> returned = saver.process(toSave);

        //verify
        verify(service).save(dailyRecordListCaptor.capture());
        assertEquals(toSave, dailyRecordListCaptor.getValue());
        assertEquals(toSave, returned);
    }

    @Test
    void process_Fail() {
        //given
        DailyRecord one = new DailyRecord();
        one.setGeoCode("01");
        one.setHealthWard("one");
        List<DailyRecord> toSave = List.of(one);

        //when
        when(service.save(toSave)).thenReturn(List.of());

        //verify
        Exception exception = assertThrows(PipeLineProcessException.class, () -> saver.process(toSave));
        assertEquals("DailyRecords not saved correctly", exception.getMessage());

        verify(service).save(dailyRecordListCaptor.capture());
        assertEquals(toSave, dailyRecordListCaptor.getValue());
    }
}