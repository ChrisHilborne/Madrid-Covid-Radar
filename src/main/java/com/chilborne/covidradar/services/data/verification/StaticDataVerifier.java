package com.chilborne.covidradar.services.data.verification;

public class StaticDataVerifier implements DataVerifier<String> {

    @Override
    public boolean verifyData(String s) {
        return true;
    }


}
