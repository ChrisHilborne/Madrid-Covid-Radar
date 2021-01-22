package com.chilborne.covidradar.data.processing.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DailyRecordFilterTest {

    DailyRecordFilter dailyRecordFilter = new DailyRecordFilter();

    @Test
    void process() {
        //given
        DailyRecord pass = new DailyRecord();
        pass.setMunicipalDistrict("Madrid-pass");
        DailyRecord fail = new DailyRecord();
        fail.setMunicipalDistrict("fail");

        List<DailyRecord> toFilter = List.of(pass, fail);

        //when
        List<DailyRecord> filtered = dailyRecordFilter.process(toFilter);

        //verify
        assertAll("filtered data",
                () -> assertEquals(1, filtered.size()),
                () -> assertEquals(pass, filtered.get(0))
        );
    }

    @Test
    void processException() {
        //given
        DailyRecord filtered = new DailyRecord();
        filtered.setMunicipalDistrict("filtered");

        List<DailyRecord> toFilter = List.of(filtered);

        //verify
        Exception exception = assertThrows(PipeLineProcessException.class,
                () -> dailyRecordFilter.process(toFilter));
        assertEquals("DailyRecordFilter removed all records", exception.getMessage());
    }
}