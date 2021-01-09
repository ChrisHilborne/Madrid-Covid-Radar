package com.chilborne.covidradar;

import com.chilborne.covidradar.services.DistrictDataService;
import com.chilborne.covidradar.services.datacollection.DataFetcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
public class CovidRadarApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(CovidRadarApplication.class, args);

		DataFetcher dataFetcher = (DataFetcher) ctx.getBean("dataFetcherTest");
		dataFetcher.fetch();
		dataFetcher.processData();

		DistrictDataService districtDataService = (DistrictDataService) ctx.getBean("districtDataServiceImpl");

		System.out.println(districtDataService.getDistrictData("079603").toString());

	}

}
