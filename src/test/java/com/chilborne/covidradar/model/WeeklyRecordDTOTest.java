package com.chilborne.covidradar.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeeklyRecordDTOTest {

    @Test
    void weeklyRecordDTO_from_WeeklyRecord() {
        //given
        LocalDate testDate = LocalDate.now();
        int cases = 100;
        int rate = 5;

        WeeklyRecord weeklyRecord = new WeeklyRecord();
        weeklyRecord.setHealthWard("weeklyRecord");
        weeklyRecord.setGeoCode("W-R");
        weeklyRecord.setDateReported(testDate);
        weeklyRecord.setCasesLastTwoWeeks(cases);
        weeklyRecord.setTotalCases(cases);
        weeklyRecord.setInfectionRateLastTwoWeeks(rate);
        weeklyRecord.setInfectionRateTotal(rate);

        //when
        WeeklyRecordDTO result = new WeeklyRecordDTO(weeklyRecord);

        //assert
        assertAll("Correct Transfer of Data",
                () -> assertEquals(weeklyRecord.getDateReported(), result.getDate()),
                () -> assertEquals(weeklyRecord.getTotalCases(), result.getTotalCases()),
                () -> assertEquals(weeklyRecord.getCasesLastTwoWeeks(), result.getTotalCases()),
                () -> assertEquals(weeklyRecord.getInfectionRateTotal(), result.getTotalRate()),
                () -> assertEquals(weeklyRecord.getInfectionRateLastTwoWeeks(), result.getTwoWeekRate())
        );
    }

}