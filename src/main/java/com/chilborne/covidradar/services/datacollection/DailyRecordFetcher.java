package com.chilborne.covidradar.services.datacollection;

import org.springframework.stereotype.Service;

@Service
public interface DailyRecordFetcher {

    void fetch();

    void processData();
}
