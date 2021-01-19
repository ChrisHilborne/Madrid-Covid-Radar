package com.chilborne.covidradar.services.dailyrecords.data.processing.interfaces;

public interface DataSorter<T> {

    public T sort(T data);
}
