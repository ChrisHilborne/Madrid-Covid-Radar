package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;

import com.chilborne.covidradar.model.WeeklyRecord;
import com.chilborne.covidradar.services.WeeklyRecordService;
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
class WeeklyRecordSaverTest {

    @Mock
    WeeklyRecordService service;

    @InjectMocks
    WeeklyRecordSaver saver;

    @Captor
    ArgumentCaptor<List<WeeklyRecord>> weeklyRecordListCaptor;

    @Test
    void process_Success() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        one.setGeoCode("01");
        one.setHealthWard("one");
        List<WeeklyRecord> toSave = List.of(one);

        //when
        when(service.save(toSave)).thenReturn(List.of(one));
        List<WeeklyRecord> returned = saver.process(toSave);

        //verify
        verify(service).save(weeklyRecordListCaptor.capture());
        assertEquals(toSave, weeklyRecordListCaptor.getValue());
        assertEquals(toSave, returned);
    }

    @Test
    void process_Fail() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        one.setGeoCode("01");
        one.setHealthWard("one");
        List<WeeklyRecord> toSave = List.of(one);

        //when
        when(service.save(toSave)).thenReturn(List.of());

        //verify
        Exception exception = assertThrows(PipeLineProcessException.class, () -> saver.process(toSave));
        assertEquals("WeeklyRecords not saved correctly", exception.getMessage());

        verify(service).save(weeklyRecordListCaptor.capture());
        assertEquals(toSave, weeklyRecordListCaptor.getValue());
    }
}