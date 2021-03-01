package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyRecordFilterTest {

    private DailyRecordFilter filter = new DailyRecordFilter();

    @Test
    void process_dates() {
        //given

        LocalDate clearPass = LocalDate.of(2020, 01, 01);
        LocalDate lastPass = LocalDate.of(2020, 05, 19);
        LocalDate cutOff = LocalDate.of(2020, 05, 20);
        LocalDate fail = LocalDate.of(2021, 01, 01);

        DailyRecord one = new DailyRecord();
        one.setDateReported(clearPass);
        DailyRecord two = new DailyRecord();
        two.setDateReported(lastPass);
        DailyRecord three = new DailyRecord();
        three.setDateReported(cutOff);
        DailyRecord four = new DailyRecord();
        four.setDateReported(fail);

        List<DailyRecord> toFilter = List.of(one, two, three, four);

        //when
        List<DailyRecord> result = filter.process(toFilter);

        //verify
        assertAll("filter dates",
                () -> assertEquals(2, result.size()),
                () -> assertEquals(one, result.get(0)),
                () -> assertEquals(two, result.get(1)));
    }
}