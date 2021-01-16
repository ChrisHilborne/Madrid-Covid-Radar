package com.chilborne.covidradar;


import com.chilborne.covidradar.services.datacollection.DailyRecordFetcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
@EnableCaching
public class CovidRadarApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(CovidRadarApplication.class, args);

		DailyRecordFetcher<String> dailyRecordFetcher = (DailyRecordFetcher) ctx.getBean("staticDailyRecordFetcher");
		dailyRecordFetcher.fetch();
	}

}
