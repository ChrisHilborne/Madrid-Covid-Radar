package com.chilborne.covidradar.services.dailyrecords.data.event;

import org.springframework.context.ApplicationEvent;

public class NewDataEvent extends ApplicationEvent {

    private String data;

    public NewDataEvent(Object source, String data) {
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
