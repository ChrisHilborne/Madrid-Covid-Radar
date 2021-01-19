package com.chilborne.covidradar.services.dailyrecords.data.verification;

public interface DataVerifier<T> {

    public boolean verifyData(T t);
}
