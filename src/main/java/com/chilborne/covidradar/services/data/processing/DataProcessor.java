package com.chilborne.covidradar.services.data.processing;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface DataProcessor<T> {

    T processData(T t);

    Map<String, T> mapData(T data);
}
