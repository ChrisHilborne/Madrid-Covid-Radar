package com.chilborne.covidradar.services.dailyrecords.data.verification;

import org.springframework.stereotype.Service;

@Service
public class StaticDataVerifier implements DataVerifier<String> {

    @Override
    public boolean verifyData(String s) {
        return true;
    }


}
