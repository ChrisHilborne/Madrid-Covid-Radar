package com.chilborne.covidradar.services;

import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.model.DistrictDataDTO;
import com.chilborne.covidradar.repository.DistrictDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistrictDataServiceImplTest {

    @Mock
    DistrictDataRepository districtDataRepository;

    @InjectMocks
    DistrictDataServiceImpl districtDataService;

    @Captor
    ArgumentCaptor<DistrictData> districtDataCaptor;


    @Test
    void getDistrictDataByNameException() {
        //given

        //when
        when(districtDataRepository.findByName("name")).thenReturn(Optional.empty());

        //verify
        assertThrows(RuntimeException.class, () -> {
            districtDataService.getDistrictDataByName("name");
        });
        verify(districtDataRepository, times(1)).findByName("name");

    }

    @Test
    void getDistrictDataByName() {
        //given
        DistrictData testDistrictData = new DistrictData();
        testDistrictData.setName("one");
        testDistrictData.setDailyRecords(new ArrayList<DailyRecord>());

        //when
        when(districtDataRepository.findByName("one")).thenReturn(Optional.of(testDistrictData));
        DistrictDataDTO result = districtDataService.getDistrictDataByName("one");

        //verify
        verify(districtDataRepository, times(1)).findByName("one");
        assertEquals("one", result.getMunicipalDistrict());
        
    }

    @Test
    void getDistrictDataByGeoCode() {
        //given
        DistrictData testDistrictData = new DistrictData();
        testDistrictData.setGeoCode("geoCode");
        testDistrictData.setDailyRecords(new ArrayList<DailyRecord>());

        //when
        when(districtDataRepository.findByGeoCode("geoCode")).thenReturn(Optional.of(testDistrictData));
        DistrictDataDTO result = districtDataService.getDistrictDataByGeoCode("geoCode");

        //verify
        verify(districtDataRepository, times(1)).findByGeoCode("geoCode");
        assertEquals("geoCode", result.getGeoCode());

    }

    @Test
    void getDistrictDataByGeoCodeException() {

        //given

        //when
        when(districtDataRepository.findByGeoCode("geoCode")).thenReturn(Optional.empty());

        //verify
        assertThrows(RuntimeException.class, () -> {
            districtDataService.getDistrictDataByGeoCode("geoCode");
        });
        verify(districtDataRepository, times(1)).findByGeoCode("geoCode");
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

        //verify
        verify(districtDataRepository, times(1)).findAll();
        assertEquals(1, testResults.size());
    }


    @Test
    void getDistrictNamesAndGeocodes() {
        //given
        DistrictData one = new DistrictData();
        one.setName("one");
        one.setGeoCode("1");
        DistrictData two = new DistrictData();
        two.setName("two");
        two.setGeoCode("2");
        List<DistrictData> results = List.of(one, two);

        //when
        when(districtDataRepository.findAll()).thenReturn(results);
        Map<String, String> namesAndGeocodes = districtDataService.getDistrictGeoCodesAndNames();

        //verify
        assertAll("names and geocodes",
                () -> assertEquals(2, namesAndGeocodes.size()),
                () -> assertEquals("1", namesAndGeocodes.get("one")),
                () -> assertEquals("2", namesAndGeocodes.get("two"))
        );
    }

    @Test
    void save() {
        //given
        DistrictData toSave = new DistrictData();
        toSave.setName("toSave");

        //when
        districtDataService.save(toSave);

        //verify
        verify(districtDataRepository, times(1)).save(toSave);
        verify(districtDataRepository).save(districtDataCaptor.capture());
        assertEquals("toSave", districtDataCaptor.getValue().getName());

    }

    @Test
    void saveList() {
        //given

        DistrictData one = new DistrictData();
        one.setName("one");
        one.setLastUpdated(LocalDate.now());
        List<DistrictData> testList = List.of(one, new DistrictData());

        //when
        districtDataService.save(testList);

        //verify
        verify(districtDataRepository, times(2)).save(any());
        verify(districtDataRepository, times(1)).save(one);
    }


}