package com.chilborne.covidradar;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class CovidRadarApplication {

	public static void main(String[] args) {

		SpringApplication.run(CovidRadarApplication.class, args);

	}

}


