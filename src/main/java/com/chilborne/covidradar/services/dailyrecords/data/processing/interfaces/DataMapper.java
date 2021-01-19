package com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces;

import java.util.Map;

public interface DataMapper<T> {

    public Map<String, T> map(T data);
}
