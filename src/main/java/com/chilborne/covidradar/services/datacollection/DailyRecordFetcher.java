package com.chilborne.covidradar.services.datacollection;

import org.springframework.stereotype.Service;

@Service
public interface DailyRecordFetcher<T> {

    void fetch();

    boolean isNewData(T t);

    void newDataEvent(T t);
}
