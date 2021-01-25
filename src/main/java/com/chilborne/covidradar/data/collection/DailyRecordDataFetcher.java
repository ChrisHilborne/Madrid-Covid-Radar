package com.chilborne.covidradar.data.collection;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface DailyRecordDataFetcher<T> {

    void collectData() throws IOException, InterruptedException;

    void publish(T t);
}
