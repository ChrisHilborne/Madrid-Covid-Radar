package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.model.DistrictDataDTO;
import com.chilborne.covidradar.repository.DistrictDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
            districtDataService.getDistrictDataByName("test");
        });
    }

    @Test
    void getDistrictDataByName() {
        
    }

    @Test
    void getDistrictDataByGeoCode() {
    }

    @Test
    void getDistrictDataByGeoCodeException() {
        Exception e = assertThrows(RuntimeException.class, () -> {
            districtDataService.getDistrictDataByGeoCode("test");
        });
    }

    @Test
    void getAllDistrictData() {
        //given
        List<DistrictData> mockResults = new ArrayList<>();
        DistrictData one = new DistrictData();
        one.setDailyRecords(new ArrayList<DailyRecord>());
        mockResults.add(one);

        //when
        when(districtDataRepository.findAll()).thenReturn(mockResults);
        List<DistrictDataDTO> testResults = districtDataService.getAllDistrictData();

        //then
        verify(districtDataRepository, times(1)).findAll();
        assertEquals(1, testResults.size());
    }

    @Test
    void getDistrictNames() {
        //given
        List<DistrictData> results = new ArrayList<>();
        DistrictData one = new DistrictData();
        one.setName("one");
        DistrictData two = new DistrictData();
        two.setName("two");
        results.add(one);
        results.add(two);

        //when
        when(districtDataRepository.findAll()).thenReturn(results);
        List<String> names = districtDataService.getDistrictNames();

        //then
        verify(districtDataRepository, times(1)).findAll();
        assertEquals(2, names.size());
        assertEquals("one", names.get(0));
    }

    @Test
    void save() {
    }


}