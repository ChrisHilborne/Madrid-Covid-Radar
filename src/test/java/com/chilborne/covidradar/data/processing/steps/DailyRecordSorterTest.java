package com.chilborne.covidradar.data.processing.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyRecordSorterTest {

    DailyRecordSorter dailyRecordSorter = new DailyRecordSorter();

    @Test
    void process() {
        //given
        DailyRecord one = new DailyRecord();
        DailyRecord two = new DailyRecord();
        List<DailyRecord> toSort = new ArrayList<>();
        toSort.add(one);
        toSort.add(two);

        //when
        List<DailyRecord> sorted = dailyRecordSorter.process(toSort);

        //verify
        assertAll("sorted",
                () -> assertEquals(two, sorted.get(0)),
                () -> assertEquals(one, sorted.get(1))
        );
    }
}