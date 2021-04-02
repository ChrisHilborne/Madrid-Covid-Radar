package com.chilborne.covidradar.data;

import org.springframework.boot.CommandLineRunner;

public interface DataInitializer extends CommandLineRunner {

    void initializeData() throws Exception;

}
