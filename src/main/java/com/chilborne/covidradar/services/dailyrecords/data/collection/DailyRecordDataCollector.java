package com.chilborne.covidradar.services.dailyrecords.data.collection;

import org.springframework.stereotype.Service;

@Service
public interface DailyRecordDataCollector<T> {

    void collectData();

    void publish(String data);
}
