package com.chilborne.covidradar.services.datacollection;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface DataProcessor {
    void processData(String data) throws JsonProcessingException;
}
