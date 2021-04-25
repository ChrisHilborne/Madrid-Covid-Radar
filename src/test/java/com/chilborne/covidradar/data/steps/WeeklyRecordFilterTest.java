package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeeklyRecordFilterTest {

    private WeeklyRecordFilter filter = new WeeklyRecordFilter();

    @Test
    void process_dates() {
        //given

        LocalDate clearPass = LocalDate.of(2020, 01, 01);
        LocalDate lastPass = LocalDate.of(2020, 05, 19);
        LocalDate cutOff = LocalDate.of(2020, 05, 20);
        LocalDate fail = LocalDate.of(2021, 01, 01);

        WeeklyRecord one = new WeeklyRecord();
        one.setDateReported(clearPass);
        WeeklyRecord two = new WeeklyRecord();
        two.setDateReported(lastPass);
        WeeklyRecord three = new WeeklyRecord();
        three.setDateReported(cutOff);
        WeeklyRecord four = new WeeklyRecord();
        four.setDateReported(fail);

        List<WeeklyRecord> toFilter = List.of(one, two, three, four);

        //when
        List<WeeklyRecord> result = filter.process(toFilter);

        //verify
        assertAll("filter dates",
                () -> assertEquals(2, result.size()),
                () -> assertEquals(one, result.get(0)),
                () -> assertEquals(two, result.get(1)));
    }
}