package com.chilborne.covidradar.events;

import org.springframework.context.ApplicationEvent;

public class UpdatedDataEvent extends ApplicationEvent {

    private String data;

    public UpdatedDataEvent(Object source, String data) {
        super(source);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
