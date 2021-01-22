package com.chilborne.covidradar.exceptions;

public class PipeLineProcessException extends RuntimeException {
    public PipeLineProcessException() {
        super();
    }

    public PipeLineProcessException(String message) {
        super(message);
    }

    public PipeLineProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public PipeLineProcessException(Throwable cause) {
        super(cause);
    }
}
