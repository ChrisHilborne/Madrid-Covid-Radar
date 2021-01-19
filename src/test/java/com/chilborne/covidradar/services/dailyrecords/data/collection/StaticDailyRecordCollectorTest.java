package com.chilborne.covidradar.services.dailyrecords.data.collection;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StaticDailyRecordCollectorTest {

    File staticDataFile = new File("./src/main/resources/static/test");

    StaticDailyRecordDataCollector staticDailyRecordDataCollector = new StaticDailyRecordDataCollector(staticDataFile);


    @Test
    void collectData() {
        //when
        String test = "This is a test.";

        //then
        String data = staticDailyRecordDataCollector.collectData();

        //verify
        assertEquals(test, data);
    }

}