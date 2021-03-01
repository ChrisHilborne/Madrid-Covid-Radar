package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Component
public class DailyRecordFilter implements Step<List<DailyRecord>, List<DailyRecord>>{

    private LocalDate date = LocalDate.of(2020, 05, 19);

    @Override
    public List<DailyRecord> process(List<DailyRecord> input) throws PipeLineProcessException {
        List<DailyRecord> result = new LinkedList<>();

        input.stream().
                forEach(record -> {
                    if (date.compareTo(record.getDateReported()) >= 0) {
                        result.add(record);
                    }
                });


        return result;
    }
}
