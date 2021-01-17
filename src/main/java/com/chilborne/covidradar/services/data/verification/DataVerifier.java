package com.chilborne.covidradar.services.data.verification;

public interface DataVerifier<T> {

    public boolean verifyData(T t);
}
