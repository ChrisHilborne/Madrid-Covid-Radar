package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
        DailyRecord three = new DailyRecord();

        one.setDateReported(LocalDate.of(2020, 12, 31));
        two.setDateReported(LocalDate.of(2020, 12, 25));
        three.setDateReported(LocalDate.of(2020, 12, 25));

        one.setHealthWard("A");
        two.setHealthWard("B");
        three.setHealthWard("A");

        List<DailyRecord> toSort = List.of(one, two, three);



        //when
        List<DailyRecord> sorted = dailyRecordSorter.process(toSort);

        //verify
        assertAll("sorted",
                () -> assertEquals(three, sorted.get(0)),
                () -> assertEquals(one, sorted.get(1)),
                () -> assertEquals(two, sorted.get(2))
        );
    }
}