
package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.DailyRecord;
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
class DailyRecordRepositoryIT {

    @Autowired
    DailyRecordRepository dailyRecordRepository;

    @Autowired
    void clear() {
        dailyRecordRepository.deleteAll();
    }

    private LocalDate now = LocalDate.now();

    @BeforeEach
    void setup() throws Exception {

        DailyRecord one = new DailyRecord();
        one.setHealthWard("one");
        one.setGeoCode("1");
        one.setDateReported(now);

        dailyRecordRepository.save(one);
    }
    

    @Test
    void findByGeoCode() {
        //when
        List<DailyRecord> optionalGeoCode = dailyRecordRepository.findByGeoCode("1");

        //verify
        assertEquals("one", optionalGeoCode.get(0).getHealthWard());
    }

    @Test
    void findByGeoCodeAndDate_Exists() {
        //when
        Optional<DailyRecord> optional = dailyRecordRepository.findByGeoCodeAndDateReported("1", now);

        //verify
        assertFalse(optional.isEmpty());
        assertEquals("one", optional.get().getHealthWard());
    }

    @Test
    void findByGeoCodeAndDate_NotExists() {
        //when
        Optional<DailyRecord> optional = dailyRecordRepository.findByGeoCodeAndDateReported("2", now);

        //verify
        assertTrue(optional.isEmpty());
    }

    @Test
    void findByHeathWardAndDate_Exists() {
        //when
        Optional<DailyRecord> optional = dailyRecordRepository.findByHealthWardAndDateReported("one", now);

        //verify
        assertFalse(optional.isEmpty());
        assertEquals("1", optional.get().getGeoCode());
    }

    @Test
    void findByHealthWardAndDate_NotExists() {
        //when
        Optional<DailyRecord> optional = dailyRecordRepository.findByHealthWardAndDateReported("two", now);

        //verify
        assertTrue(optional.isEmpty());
    }
}

