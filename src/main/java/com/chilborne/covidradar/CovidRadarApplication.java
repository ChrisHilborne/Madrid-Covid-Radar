package com.chilborne.covidradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CovidRadarApplication {

	public static void main(String[] args) {

		SpringApplication.run(CovidRadarApplication.class, args);

	}

}
