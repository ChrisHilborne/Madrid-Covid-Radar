package com.chilborne.covidradar.repository;

import com.chilborne.covidradar.model.DistrictData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@DirtiesContext
@ExtendWith(SpringExtension.class)
class DistrictDataRepositoryIT {


    @Autowired
    DistrictDataRepository districtDataRepository;

    @Autowired
    void clear() {
        districtDataRepository.deleteAll();
    }

    @BeforeEach
    void setup() throws Exception {

        DistrictData one = new DistrictData();
        one.setName("one");
        one.setGeoCode("1");

        districtDataRepository.save(one);
    }


    @Test
    void findByNameExists() {
        //when
        Optional<DistrictData> optionalName = districtDataRepository.findByName("one");

        //verify
        assertTrue(optionalName.isPresent());
        assertEquals("1", optionalName.get().getGeoCode());
    }

    @Test
    void findByNameNotExist() {
        //when
        Optional<DistrictData> optionalName = districtDataRepository.findByName("two");

        //verify
        assertTrue(optionalName.isEmpty());
    }

    @Test
    void findByGeoCodeExists() {
        //when
        Optional<DistrictData> optionalGeoCode = districtDataRepository.findByGeoCode("1");

        //verify
        assertEquals(true, optionalGeoCode.isPresent());
        assertEquals("one", optionalGeoCode.get().getName());
    }

    @Test
    void findByGeoCodeNotExist() {
        //when
        Optional<DistrictData> optionalGeoCode = districtDataRepository.findByGeoCode("2");

        //verify
        assertTrue(optionalGeoCode.isEmpty());
    }
}