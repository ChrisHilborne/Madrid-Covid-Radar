package com.chilborne.covidradar.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyRecordTest {


    @Test
    void generateId() {
        //given
        String healthWard = "healthWard";
        LocalDate testDate = LocalDate.of(2020, 1, 1);

        DailyRecord test = new DailyRecord();
        test.setHealthWard(healthWard);
        test.setDateReported(testDate);

        //when
        test.generateId();
        String id = test.getId();

        //verify
        assertEquals("healthWard::2020-01-01", id);
    }
}