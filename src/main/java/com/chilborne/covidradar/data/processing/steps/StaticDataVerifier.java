package com.chilborne.covidradar.data.processing.steps;

import org.springframework.stereotype.Component;

@Component
public class StaticDataVerifier implements Step<String, String> {

    @Override
    public String process(String input) {

        return input;
    }


}
