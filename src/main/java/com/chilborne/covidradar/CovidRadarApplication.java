package com.chilborne.covidradar;


import com.chilborne.covidradar.data.collection.DailyRecordDataFetcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
@EnableConfigurationProperties
@EnableCaching
public class CovidRadarApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(CovidRadarApplication.class, args);

		DailyRecordDataFetcher<String> dataCollector = (DailyRecordDataFetcher<String>) ctx.getBean("httpDailyRecordFetcher");

		try {
			dataCollector.collectData();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

}
