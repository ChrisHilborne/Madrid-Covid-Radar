package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Component
public class WeeklyRecordFilter implements Step<List<WeeklyRecord>, List<WeeklyRecord>>{

    private LocalDate date = LocalDate.of(2020, 5, 19);

    @Override
    public List<WeeklyRecord> process(List<WeeklyRecord> input) throws PipeLineProcessException {
        List<WeeklyRecord> result = new LinkedList<>();

        input.forEach(weeklyRecord -> {
                    if (date.compareTo(weeklyRecord.getDateReported()) >= 0) {
                        result.add(weeklyRecord);
                    }
                });


        return result;
    }
}
