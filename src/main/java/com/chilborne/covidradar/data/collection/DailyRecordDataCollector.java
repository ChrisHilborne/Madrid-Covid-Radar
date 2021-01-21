package com.chilborne.covidradar.data.collection;

import org.springframework.stereotype.Service;

@Service
public interface DailyRecordDataCollector<T> {

    void collectData();

    void publish(String data);
}
