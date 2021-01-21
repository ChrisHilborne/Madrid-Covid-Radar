package com.chilborne.covidradar.data.processing.pipeline;

import com.chilborne.covidradar.data.processing.steps.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PipelineTest {

    Pipeline<Integer, Integer> testPipeline;

    @BeforeEach
    void init() {
        Step<Integer, Integer> multiplyBy2 = input -> input * 2;

        testPipeline = new Pipeline<>(multiplyBy2);
    }

    @Test
    void pipe() {
        //given
        int n = 2;
        Step<Integer, String> toString = input -> input.toString();

        //when
        Pipeline<Integer, String> pipedPieLine = testPipeline.pipe(toString);
        String output = pipedPieLine.execute(n);

        //verify
        assertEquals("4", output);

    }

    @Test
    void execute() {
        //given
        int n = 2;

        //when
        int output = testPipeline.execute(n);

        //verify
        assertEquals(4, output);

    }
}