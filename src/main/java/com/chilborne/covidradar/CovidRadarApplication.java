package com.chilborne.covidradar;

import com.chilborne.covidradar.services.datacollection.DailyFigureFetcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
public class CovidRadarApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(CovidRadarApplication.class, args);

		DailyFigureFetcher dailyRecordFetcher = (DailyFigureFetcher) ctx.getBean("staticDailyRecordFetcher");
		dailyRecordFetcher.fetch();
		dailyRecordFetcher.processData();
	}

}
