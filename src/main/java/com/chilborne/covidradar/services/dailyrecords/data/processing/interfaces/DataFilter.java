package com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces;

public interface DataFilter<T> {

    public T filter(T data);
}
