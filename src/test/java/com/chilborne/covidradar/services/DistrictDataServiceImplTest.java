package com.chilborne.covidradar.services;

import com.chilborne.covidradar.repository.DistrictDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DistrictDataServiceImplTest {


    DistrictDataServiceImpl districtDataService;

    @Mock
    DistrictDataRepository districtDataRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        districtDataService = new DistrictDataServiceImpl(districtDataRepository);
    }

    @Test
    void getDistrictDataByNameException() {
        Exception e = assertThrows(RuntimeException.class, () -> {
            districtDataService.getDistrictDataByName("retiro");
        });
    }

    @Test
    void getDistrictDataByName() {
        
    }

    @Test
    void getDistrictDataByGeoCode() {
    }

    @Test
    void getAllDistrictData() {
    }

    @Test
    void getDistrictNames() {
    }

    @Test
    void save() {
    }


}