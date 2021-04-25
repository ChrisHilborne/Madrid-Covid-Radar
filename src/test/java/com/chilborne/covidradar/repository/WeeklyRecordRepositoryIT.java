
package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.WeeklyRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@DirtiesContext
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class WeeklyRecordRepositoryIT {

    @Autowired
    WeeklyRecordRepository weeklyRecordRepository;

    @Autowired
    void clear() {
        weeklyRecordRepository.deleteAll();
    }

    private LocalDate now = LocalDate.now();

    @BeforeEach
    void setup() throws Exception {

        WeeklyRecord one = new WeeklyRecord();
        one.setHealthWard("one");
        one.setGeoCode("1");
        one.setDateReported(now);

        weeklyRecordRepository.save(one);
    }
    

    @Test
    void findByGeoCode() {
        //when
        List<WeeklyRecord> optionalGeoCode = weeklyRecordRepository.findByGeoCode("1");

        //verify
        assertEquals("one", optionalGeoCode.get(0).getHealthWard());
    }

    @Test
    void findByGeoCodeAndDate_Exists() {
        //when
        Optional<WeeklyRecord> optional = weeklyRecordRepository.findByGeoCodeAndDateReported("1", now);

        //verify
        assertFalse(optional.isEmpty());
        assertEquals("one", optional.get().getHealthWard());
    }

    @Test
    void findByGeoCodeAndDate_NotExists() {
        //when
        Optional<WeeklyRecord> optional = weeklyRecordRepository.findByGeoCodeAndDateReported("2", now);

        //verify
        assertTrue(optional.isEmpty());
    }

    @Test
    void findByHeathWardAndDate_Exists() {
        //when
        Optional<WeeklyRecord> optional = weeklyRecordRepository.findByHealthWardAndDateReported("one", now);

        //verify
        assertFalse(optional.isEmpty());
        assertEquals("1", optional.get().getGeoCode());
    }

    @Test
    void findByHealthWardAndDate_NotExists() {
        //when
        Optional<WeeklyRecord> optional = weeklyRecordRepository.findByHealthWardAndDateReported("two", now);

        //verify
        assertTrue(optional.isEmpty());
    }
}

