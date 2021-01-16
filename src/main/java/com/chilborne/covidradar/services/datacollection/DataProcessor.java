package com.chilborne.covidradar.services.datacollection;

import com.chilborne.covidradar.events.NewDataEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface DataProcessor {

    void processData(NewDataEvent newData) throws JsonProcessingException;
}
