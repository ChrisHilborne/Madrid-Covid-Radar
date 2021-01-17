package com.chilborne.covidradar.services.data.processing;

public interface DataSaver <T> {

    public void saveData(T data);
}
