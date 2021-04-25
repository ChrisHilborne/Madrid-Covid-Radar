package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyRecordTrimmerTest {

    WeeklyRecordFixer weeklyRecordTrimmer = new WeeklyRecordFixer();

    @Test
    void process() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        one.setInfectionRateTotal(101);
        one.setInfectionRateLastTwoWeeks(10);
        one.setHealthWard("Test Test");

        List<WeeklyRecord> dataToTrim = List.of(one);

        //when
        List<WeeklyRecord> trimmedData = weeklyRecordTrimmer.process(dataToTrim);
        WeeklyRecord trimmedRecord = trimmedData.get(0);

        //verify
        assertAll("trimming",
                () -> assertEquals(101, trimmedRecord.getInfectionRateTotal()),
                () -> assertEquals(10, trimmedRecord.getInfectionRateLastTwoWeeks()),
                () -> assertEquals("test_test", trimmedRecord.getGeoCode())
        );
    }


    @Test
    void processException_TwoWeekInfectionRateTooLow() {
        //given
        WeeklyRecord test = new WeeklyRecord();
        test.setHealthWard("lowTwoWeekInfectionRate");
        test.setInfectionRateLastTwoWeeks(-10);
        test.setGeoCode("test");

        //when
        Exception twoWeekInfectionRateTooLow = assertThrows(PipeLineProcessException.class,
                () -> weeklyRecordTrimmer.process(List.of(test)));

        //verify
        assertEquals("Two Week Infection Rate rounded to less than zero",
                twoWeekInfectionRateTooLow.getMessage());
    }

    @Test
    void processException_TotalInfectionRateTooLow() {
        WeeklyRecord test = new WeeklyRecord();
        test.setHealthWard("lowTotalInfectionRate");
        test.setInfectionRateTotal(-10);
        test.setGeoCode("test");

        //when
        Exception totalInfectionRateTooLow = assertThrows(PipeLineProcessException.class,
                () -> weeklyRecordTrimmer.process(List.of(test)));

        //verify
        assertEquals("Total Infection Rate rounded to less than zero",
                totalInfectionRateTooLow.getMessage());
    }
}