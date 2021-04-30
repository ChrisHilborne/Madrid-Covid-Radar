package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.WeeklyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class WeeklyRecordAggregator implements Step<Map<String,List<WeeklyRecord>>, List<WeeklyRecord>> {

    private final Logger logger = LoggerFactory.getLogger(WeeklyRecordAggregator.class);

    @Override
    public List<WeeklyRecord> process(Map<String,List<WeeklyRecord>> input) throws PipeLineProcessException {
        logger.debug("Aggregating WeeklyRecords by week");
        LinkedList<WeeklyRecord> results = new LinkedList<>();

        for (List<WeeklyRecord> weeklyRecordList : input.values()) {

            for (int i = 0; i < weeklyRecordList.size(); i += 7) {
                WeeklyRecord firstRecord = weeklyRecordList.get(i);
                ArrayList<WeeklyRecord> toAggregate = new ArrayList<>(6);
                for (int j = i + 1; j <= i + 6; j++) {
                    if (j == weeklyRecordList.size()) {
                        break;
                    } else {
                        toAggregate.add(weeklyRecordList.get(j));
                    }
                }
                WeeklyRecord aggregate = aggregateWeeklyRecords(firstRecord, toAggregate);
                results.add(aggregate);
            }
        }

        return results;
    }

    private WeeklyRecord aggregateWeeklyRecords(WeeklyRecord firstRecord, ArrayList<WeeklyRecord> toAggregate) {
        WeeklyRecord result = firstRecord;
        int size = toAggregate.size() + 1;
        toAggregate.stream()
                .forEach(record -> {
                    result.setTotalCases(result.getTotalCases() + record.getTotalCases());
                    result.setInfectionRateTotal(result.getInfectionRateTotal() + record.getInfectionRateTotal());
                    result.setCasesLastTwoWeeks(result.getCasesLastTwoWeeks() + record.getCasesLastTwoWeeks());
                    result.setInfectionRateLastTwoWeeks(result.getInfectionRateLastTwoWeeks() + record.getInfectionRateLastTwoWeeks() );
                });
        result.setTotalCases(result.getTotalCases() / size);
        result.setInfectionRateTotal(result.getInfectionRateTotal() / size);
        result.setCasesLastTwoWeeks(result.getCasesLastTwoWeeks() / size);
        result.setInfectionRateLastTwoWeeks(result.getInfectionRateLastTwoWeeks() / size);

        return result;
    }


}

