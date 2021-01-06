package com.chilborne.covidradar;

import com.chilborne.covidradar.model.DailyFigure;
import com.chilborne.covidradar.services.DailyFigureService;
import com.chilborne.covidradar.services.datacollection.DataFetcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class CovidRadarApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(CovidRadarApplication.class, args);

		DataFetcher dataFetcher = (DataFetcher) ctx.getBean("dataFetcherTest");
		dataFetcher.fetch();
		dataFetcher.processData();

		DailyFigureService dailyFigureService = (DailyFigureService) ctx.getBean("dailyFigureServiceTest");

		List<DailyFigure> list = dailyFigureService.getDailyFigures("079603");

		list.stream()
				.forEach(df -> System.out.println(df.toString()));

	}

}
