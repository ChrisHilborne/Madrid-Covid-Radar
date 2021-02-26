package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DailyRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyRecordAggregatorTest {

    DailyRecordAggregator aggregator = new DailyRecordAggregator();

    @Test
    void process_7_Records() {
        //given
        DailyRecord one = new DailyRecord();
        one.setTotalCases(1);
        one.setInfectionRateTotal(1);
        one.setCasesLastTwoWeeks(1);
        one.setInfectionRateLastTwoWeeks(1);
        DailyRecord two = new DailyRecord();
        two.setTotalCases(2);
        two.setInfectionRateTotal(2);
        two.setCasesLastTwoWeeks(2);
        two.setInfectionRateLastTwoWeeks(2);
        DailyRecord three = new DailyRecord();
        three.setTotalCases(3);
        three.setInfectionRateTotal(3);
        three.setCasesLastTwoWeeks(3);
        three.setInfectionRateLastTwoWeeks(3);
        DailyRecord four = new DailyRecord();
        four.setTotalCases(4);
        four.setInfectionRateTotal(4);
        four.setCasesLastTwoWeeks(4);
        four.setInfectionRateLastTwoWeeks(4);
        DailyRecord five = new DailyRecord();
        five.setTotalCases(5);
        five.setInfectionRateTotal(5);
        five.setCasesLastTwoWeeks(5);
        five.setInfectionRateLastTwoWeeks(5);
        DailyRecord six = new DailyRecord();
        six.setTotalCases(6);
        six.setInfectionRateTotal(6);
        six.setCasesLastTwoWeeks(6);
        six.setInfectionRateLastTwoWeeks(6);
        DailyRecord seven = new DailyRecord();
        seven.setTotalCases(7);
        seven.setInfectionRateTotal(7);
        seven.setCasesLastTwoWeeks(7);
        seven.setInfectionRateLastTwoWeeks(7);

        List<DailyRecord> toAggregate = List.of(one, two, three, four, five, six, seven);

        //when
        List<DailyRecord> results = aggregator.process(toAggregate);


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
        DailyRecord three = new DailyRecord();
        three.setTotalCases(3);
        three.setInfectionRateTotal(3);
        three.setCasesLastTwoWeeks(3);
        three.setInfectionRateLastTwoWeeks(3);
        DailyRecord nine = new DailyRecord();
        nine.setTotalCases(9);
        nine.setInfectionRateTotal(9);
        nine.setCasesLastTwoWeeks(9);
        nine.setInfectionRateLastTwoWeeks(9);
        DailyRecord twelve = new DailyRecord();
        twelve.setTotalCases(12);
        twelve.setInfectionRateTotal(12);
        twelve.setCasesLastTwoWeeks(12);
        twelve.setInfectionRateLastTwoWeeks(12);

        List<DailyRecord> toAggregate = List.of(three, nine, twelve);

        //when
        List<DailyRecord> results = aggregator.process(toAggregate);

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
        DailyRecord one = new DailyRecord();
        one.setTotalCases(1);
        one.setInfectionRateTotal(1);
        one.setCasesLastTwoWeeks(1);
        one.setInfectionRateLastTwoWeeks(1);
        DailyRecord two = new DailyRecord();
        two.setTotalCases(2);
        two.setInfectionRateTotal(2);
        two.setCasesLastTwoWeeks(2);
        two.setInfectionRateLastTwoWeeks(2);
        DailyRecord three = new DailyRecord();
        three.setTotalCases(3);
        three.setInfectionRateTotal(3);
        three.setCasesLastTwoWeeks(3);
        three.setInfectionRateLastTwoWeeks(3);
        DailyRecord four = new DailyRecord();
        four.setTotalCases(4);
        four.setInfectionRateTotal(4);
        four.setCasesLastTwoWeeks(4);
        four.setInfectionRateLastTwoWeeks(4);
        DailyRecord five = new DailyRecord();
        five.setTotalCases(5);
        five.setInfectionRateTotal(5);
        five.setCasesLastTwoWeeks(5);
        five.setInfectionRateLastTwoWeeks(5);
        DailyRecord six = new DailyRecord();
        six.setTotalCases(6);
        six.setInfectionRateTotal(6);
        six.setCasesLastTwoWeeks(6);
        six.setInfectionRateLastTwoWeeks(6);
        DailyRecord seven = new DailyRecord();
        seven.setTotalCases(7);
        seven.setInfectionRateTotal(7);
        seven.setCasesLastTwoWeeks(7);
        seven.setInfectionRateLastTwoWeeks(7);

        DailyRecord oneHundred = new DailyRecord();
        oneHundred.setTotalCases(100);
        oneHundred.setInfectionRateTotal(100);
        oneHundred.setCasesLastTwoWeeks(100);
        oneHundred.setInfectionRateLastTwoWeeks(100);

        List<DailyRecord> toAggregate = List.of(one, two, three, four, five, six, seven, oneHundred);

        //when
        List<DailyRecord> results = aggregator.process(toAggregate);

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