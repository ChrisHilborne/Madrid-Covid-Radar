package com.chilborne.covidradar.services.dailyrecords.data.processing.pipeline;

import com.chilborne.covidradar.services.dailyrecords.data.processing.steps.Step;


public class Pipeline<I, O> {
    private final Step<I, O> current;
    public Pipeline(Step<I, O> current) {
        this.current = current;
    }

    public <NewO> Pipeline<I, NewO> pipe(Step<O, NewO> next) {
        return new Pipeline<>(input -> next.process(current.process(input)));
    }

    public O execute(I input) throws Step.PipeLineProcessException {
        return current.process(input);
    }
}
