package com.chilborne.covidradar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
class CovidRadarApplicationTests {

	@Test
	void contextLoads() {
	}

}
