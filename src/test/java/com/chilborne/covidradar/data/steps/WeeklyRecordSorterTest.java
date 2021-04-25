package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeeklyRecordSorterTest {

    WeeklyRecordSorter weeklyRecordSorter = new WeeklyRecordSorter();

    @Test
    void process() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        WeeklyRecord two = new WeeklyRecord();
        WeeklyRecord three = new WeeklyRecord();

        one.setDateReported(LocalDate.of(2020, 12, 31));
        two.setDateReported(LocalDate.of(2020, 12, 25));
        three.setDateReported(LocalDate.of(2020, 12, 25));

        one.setHealthWard("A");
        two.setHealthWard("B");
        three.setHealthWard("A");

        List<WeeklyRecord> toSort = List.of(one, two, three);



        //when
        List<WeeklyRecord> sorted = weeklyRecordSorter.process(toSort);

        //verify
        assertAll("sorted",
                () -> assertEquals(three, sorted.get(0)),
                () -> assertEquals(one, sorted.get(1)),
                () -> assertEquals(two, sorted.get(2))
        );
    }
}