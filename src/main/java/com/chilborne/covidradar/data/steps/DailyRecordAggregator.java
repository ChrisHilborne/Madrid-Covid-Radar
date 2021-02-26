package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.exceptions.PipeLineProcessException;
import com.chilborne.covidradar.model.DailyRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class DailyRecordAggregator implements Step<List<DailyRecord>, List<DailyRecord>> {

    @Override
    public List<DailyRecord> process(List<DailyRecord> input) throws PipeLineProcessException {
        LinkedList<DailyRecord> results = new LinkedList<>();

        for (int i = 0; i < input.size(); i+=7) {
            DailyRecord firstRecord = input.get(i);
            ArrayList<DailyRecord> toAggregate = new ArrayList<>(6);
            for (int j = i + 1; j <= i + 6; j++) {
                if (j == input.size()) {
                    break;
                }
                else {
                    toAggregate.add(input.get(j));
                }
            }
            DailyRecord aggregate = aggregateDailyRecords(firstRecord, toAggregate);
            results.add(aggregate);
        }

        return results;
    }

    private DailyRecord aggregateDailyRecords(DailyRecord firstRecord,ArrayList<DailyRecord> toAggregate) {
        DailyRecord result = firstRecord;
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

