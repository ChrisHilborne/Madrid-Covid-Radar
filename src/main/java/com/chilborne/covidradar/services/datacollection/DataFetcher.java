package com.chilborne.covidradar.services.datacollection;

import org.springframework.stereotype.Service;

@Service
public interface DataFetcher {

    void fetch();

    void processData();
}
