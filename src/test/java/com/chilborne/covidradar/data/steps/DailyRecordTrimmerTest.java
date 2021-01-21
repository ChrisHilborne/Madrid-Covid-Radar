package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyRecordTrimmerTest {

    DailyRecordTrimmer dailyRecordTrimmer = new DailyRecordTrimmer();

    @Test
    void process() {
        //given
        DailyRecord one = new DailyRecord();
        one.setMunicipalDistrict("Madrid-one");
        one.setInfectionRateTotal(100.543);
        one.setInfectionRateLastTwoWeeks(10.999);

        List<DailyRecord> dataToTrim = List.of(one);

        //when
        List<DailyRecord> trimmedData = dailyRecordTrimmer.process(dataToTrim);
        DailyRecord trimmedRecord = trimmedData.get(0);

        //verify
        assertAll("trimming",
                () -> assertEquals("one", trimmedRecord.getMunicipalDistrict()),
                () -> assertEquals(100.54, trimmedRecord.getInfectionRateTotal()),
                () -> assertEquals(11.00, trimmedRecord.getInfectionRateLastTwoWeeks())
        );
    }
}