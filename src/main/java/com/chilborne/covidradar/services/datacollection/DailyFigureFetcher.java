package com.chilborne.covidradar.services.datacollection;

import org.springframework.stereotype.Service;

@Service
public interface DailyFigureFetcher {

    void fetch();

    void processData();
}
