package com.chilborne.covidradar.data.processing.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}