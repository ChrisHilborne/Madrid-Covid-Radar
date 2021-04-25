package com.chilborne.covidradar.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyRecordParserConfig {

    @Bean
    public ObjectMapper ObjectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

}
