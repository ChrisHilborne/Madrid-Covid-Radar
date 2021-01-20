package com.chilborne.covidradar.services.dailyrecords.data.processing.steps;

public interface Step<I, O> {
    public static class PipeLineProcessException extends RuntimeException {
        public PipeLineProcessException(Throwable t) {
            super(t);
        }

        public PipeLineProcessException() {
        }
    }

    public O process(I input) throws PipeLineProcessException;

}
