package com.chilborne.covidradar.exceptions;

public class DataFetchException extends RuntimeException {

    public DataFetchException() {
        super();
    }

    public DataFetchException(String message) {
        super(message);
    }

    public DataFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
