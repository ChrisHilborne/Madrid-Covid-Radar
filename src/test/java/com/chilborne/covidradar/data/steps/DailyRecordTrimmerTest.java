package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DailyRecordTrimmerTest {

    DailyRecordTrimmer dailyRecordTrimmer = new DailyRecordTrimmer();

    @Test
    void process() {
        //given
        DailyRecord one = new DailyRecord();
        one.setHealthWard("Madrid-one");
        one.setInfectionRateTotal(100.543);
        one.setInfectionRateLastTwoWeeks(10.999);

        List<DailyRecord> dataToTrim = List.of(one);

        //when
        List<DailyRecord> trimmedData = dailyRecordTrimmer.process(dataToTrim);
        DailyRecord trimmedRecord = trimmedData.get(0);

        //verify
        assertAll("trimming",
                () -> assertEquals("one", trimmedRecord.getHealthWard()),
                () -> assertEquals(100.54, trimmedRecord.getInfectionRateTotal()),
                () -> assertEquals(11.00, trimmedRecord.getInfectionRateLastTwoWeeks())
        );
    }

    @Test
    void processException_MunicipalDistrictTooShort() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("Madrid-");


        //when
        Exception municipalDistrict = assertThrows(PipeLineProcessException.class,
                () -> dailyRecordTrimmer.process(List.of(test)));


        //verify
        assertEquals("DailyRecord MunicipalDistrict trimmed to length 0.", municipalDistrict.getMessage());

    }

    @Test
    void processException_TwoWeekInfectionRateTooLow() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("lowTwoWeekInfectionRate");
        test.setInfectionRateLastTwoWeeks(-10.00);

        //when
        Exception twoWeekInfectionRateTooLow = assertThrows(PipeLineProcessException.class,
                () -> dailyRecordTrimmer.process(List.of(test)));

        //verify
        assertEquals("Two Week Infection Rate rounded to less than zero",
                twoWeekInfectionRateTooLow.getMessage());
    }

    @Test
    void processException_TotalInfectionRateTooLow() {
        DailyRecord test = new DailyRecord();
        test.setHealthWard("lowTotalInfectionRate");
        test.setInfectionRateTotal(-10.00);

        //when
        Exception totalInfectionRateTooLow = assertThrows(PipeLineProcessException.class,
                () -> dailyRecordTrimmer.process(List.of(test)));

        //verify
        assertEquals("Total Infection Rate rounded to less than zero",
                totalInfectionRateTooLow.getMessage());
    }
}