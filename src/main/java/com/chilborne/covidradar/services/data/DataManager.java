package com.chilborne.covidradar.services.data;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface DataManager {

    public boolean getData() throws JsonProcessingException;

}
