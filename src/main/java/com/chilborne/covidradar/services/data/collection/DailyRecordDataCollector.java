package com.chilborne.covidradar.services.data.collection;

import org.springframework.stereotype.Service;

@Service
public interface DailyRecordDataCollector<T> {

    String collectData();
}
