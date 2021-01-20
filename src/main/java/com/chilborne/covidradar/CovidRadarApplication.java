package com.chilborne.covidradar;


import com.chilborne.covidradar.services.dailyrecords.data.DataManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
@EnableConfigurationProperties
@EnableCaching
public class CovidRadarApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(CovidRadarApplication.class, args);

		DataManager dataManager = (DataManager) ctx.getBean("dailyRecordDataManager");

		dataManager.newData();

	}

}
