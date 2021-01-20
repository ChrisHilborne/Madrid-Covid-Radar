package com.chilborne.covidradar.services.dailyrecords.data.processing.steps;

import org.springframework.stereotype.Component;

@Component
public class StaticDataVerifier implements Step<String, String> {

    @Override
    public String process(String input) {

        return input;
    }


}
