package com.chilborne.covidradar.exceptions;

public class DataSaveException extends RuntimeException {
    public DataSaveException() {
        super();
    }

    public DataSaveException(String message) {
        super(message);
    }

    public DataSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
