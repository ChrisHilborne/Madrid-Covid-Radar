package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeeklyRecordAggregatorTest {

    private WeeklyRecordAggregator aggregator = new WeeklyRecordAggregator();

    @Test
    void process_7_Records() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        one.setTotalCases(1);
        one.setInfectionRateTotal(1);
        one.setCasesLastTwoWeeks(1);
        one.setInfectionRateLastTwoWeeks(1);
        WeeklyRecord two = new WeeklyRecord();
        two.setTotalCases(2);
        two.setInfectionRateTotal(2);
        two.setCasesLastTwoWeeks(2);
        two.setInfectionRateLastTwoWeeks(2);
        WeeklyRecord three = new WeeklyRecord();
        three.setTotalCases(3);
        three.setInfectionRateTotal(3);
        three.setCasesLastTwoWeeks(3);
        three.setInfectionRateLastTwoWeeks(3);
        WeeklyRecord four = new WeeklyRecord();
        four.setTotalCases(4);
        four.setInfectionRateTotal(4);
        four.setCasesLastTwoWeeks(4);
        four.setInfectionRateLastTwoWeeks(4);
        WeeklyRecord five = new WeeklyRecord();
        five.setTotalCases(5);
        five.setInfectionRateTotal(5);
        five.setCasesLastTwoWeeks(5);
        five.setInfectionRateLastTwoWeeks(5);
        WeeklyRecord six = new WeeklyRecord();
        six.setTotalCases(6);
        six.setInfectionRateTotal(6);
        six.setCasesLastTwoWeeks(6);
        six.setInfectionRateLastTwoWeeks(6);
        WeeklyRecord seven = new WeeklyRecord();
        seven.setTotalCases(7);
        seven.setInfectionRateTotal(7);
        seven.setCasesLastTwoWeeks(7);
        seven.setInfectionRateLastTwoWeeks(7);

        List<WeeklyRecord> toAggregate = List.of(one, two, three, four, five, six, seven);

        HashMap<String, List<WeeklyRecord>> map = new HashMap<>();
        map.put("test", toAggregate);

        //when
        List<WeeklyRecord> results = aggregator.process(map);


        //verify
        assertAll("one week",
                () -> assertEquals(1, results.size()),
                () -> assertEquals(4, results.get(0).getTotalCases()),
                () -> assertEquals(4, results.get(0).getInfectionRateTotal()),
                () -> assertEquals(4, results.get(0).getCasesLastTwoWeeks()),
                () -> assertEquals(4, results.get(0).getInfectionRateLastTwoWeeks())
        );

    }


    @Test
    void process_3_Records() {
        //given
        WeeklyRecord three = new WeeklyRecord();
        three.setTotalCases(3);
        three.setInfectionRateTotal(3);
        three.setCasesLastTwoWeeks(3);
        three.setInfectionRateLastTwoWeeks(3);
        WeeklyRecord nine = new WeeklyRecord();
        nine.setTotalCases(9);
        nine.setInfectionRateTotal(9);
        nine.setCasesLastTwoWeeks(9);
        nine.setInfectionRateLastTwoWeeks(9);
        WeeklyRecord twelve = new WeeklyRecord();
        twelve.setTotalCases(12);
        twelve.setInfectionRateTotal(12);
        twelve.setCasesLastTwoWeeks(12);
        twelve.setInfectionRateLastTwoWeeks(12);

        List<WeeklyRecord> toAggregate = List.of(three, nine, twelve);

        HashMap<String, List<WeeklyRecord>> map = new HashMap<>();

        map.put("test", toAggregate);

        //when
        List<WeeklyRecord> results = aggregator.process(map);

        //verify
        assertAll("three records",
                () -> assertEquals(1, results.size()),
                () -> assertEquals(8, results.get(0).getTotalCases()),
                () -> assertEquals(8, results.get(0).getInfectionRateTotal()),
                () -> assertEquals(8, results.get(0).getCasesLastTwoWeeks()),
                () -> assertEquals(8, results.get(0).getInfectionRateLastTwoWeeks())
        );

    }

    @Test
    void process_8_Records() {
        //given
        WeeklyRecord one = new WeeklyRecord();
        one.setTotalCases(1);
        one.setInfectionRateTotal(1);
        one.setCasesLastTwoWeeks(1);
        one.setInfectionRateLastTwoWeeks(1);
        WeeklyRecord two = new WeeklyRecord();
        two.setTotalCases(2);
        two.setInfectionRateTotal(2);
        two.setCasesLastTwoWeeks(2);
        two.setInfectionRateLastTwoWeeks(2);
        WeeklyRecord three = new WeeklyRecord();
        three.setTotalCases(3);
        three.setInfectionRateTotal(3);
        three.setCasesLastTwoWeeks(3);
        three.setInfectionRateLastTwoWeeks(3);
        WeeklyRecord four = new WeeklyRecord();
        four.setTotalCases(4);
        four.setInfectionRateTotal(4);
        four.setCasesLastTwoWeeks(4);
        four.setInfectionRateLastTwoWeeks(4);
        WeeklyRecord five = new WeeklyRecord();
        five.setTotalCases(5);
        five.setInfectionRateTotal(5);
        five.setCasesLastTwoWeeks(5);
        five.setInfectionRateLastTwoWeeks(5);
        WeeklyRecord six = new WeeklyRecord();
        six.setTotalCases(6);
        six.setInfectionRateTotal(6);
        six.setCasesLastTwoWeeks(6);
        six.setInfectionRateLastTwoWeeks(6);
        WeeklyRecord seven = new WeeklyRecord();
        seven.setTotalCases(7);
        seven.setInfectionRateTotal(7);
        seven.setCasesLastTwoWeeks(7);
        seven.setInfectionRateLastTwoWeeks(7);

        WeeklyRecord oneHundred = new WeeklyRecord();
        oneHundred.setTotalCases(100);
        oneHundred.setInfectionRateTotal(100);
        oneHundred.setCasesLastTwoWeeks(100);
        oneHundred.setInfectionRateLastTwoWeeks(100);

        List<WeeklyRecord> toAggregate = List.of(one, two, three, four, five, six, seven, oneHundred);

        HashMap<String, List<WeeklyRecord>> map = new HashMap<>();

        map.put("test", toAggregate);

        //when
        List<WeeklyRecord> results = aggregator.process(map);

        //verify
        assertAll("one week + one day",
                () -> assertEquals(2, results.size()),
                () -> assertEquals(4, results.get(0).getTotalCases()),
                () -> assertEquals(4, results.get(0).getInfectionRateTotal()),
                () -> assertEquals(4, results.get(0).getCasesLastTwoWeeks()),
                () -> assertEquals(4, results.get(0).getInfectionRateLastTwoWeeks()),
                () -> assertEquals(oneHundred, results.get(1))
        );

    }
}