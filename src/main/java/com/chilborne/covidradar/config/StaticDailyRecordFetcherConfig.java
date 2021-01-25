package com.chilborne.covidradar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "data.static")
public class StaticDailyRecordFetcherConfig {

    private String filepath;

    @Bean
    public File staticDataFile() {
        return new File(filepath);
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
