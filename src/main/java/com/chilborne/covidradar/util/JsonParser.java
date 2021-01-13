package com.chilborne.covidradar.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface JsonParser<T> {

    List<T> parse(String json) throws JsonProcessingException;

}
