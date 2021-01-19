package com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces;

public interface DataTrimmer<T> {

    public T trim(T data);
}
