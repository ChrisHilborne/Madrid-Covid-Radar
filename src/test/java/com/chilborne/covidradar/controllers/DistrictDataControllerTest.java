package com.chilborne.covidradar.controllers;

import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.model.HealthWard;
import com.chilborne.covidradar.services.HealthWardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DistrictDataControllerTest {

    MockMvc mvc;

    LocalDate testDate;

    long testDateEpochMilli;

    @Mock
    HealthWardService districtDataService;

    @InjectMocks
    HealthWardController districtDataController;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(districtDataController).build();

        testDate = LocalDate.now();
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        testDateEpochMilli = testDate.atStartOfDay(zoneId).toInstant().toEpochMilli();
    }

    @Test
    void getAllDistrictData_StatusIsOK() throws Exception {
        //given
        HealthWard testDTO = new HealthWard();
        testDTO.setName("test");
        testDTO.setLastUpdated(testDate);
        List<HealthWard> testList = List.of(testDTO);

        //when
        when(districtDataService.getAllHealthWards()).thenReturn(testList);

        //verify
        mvc.perform(
                 get("/data/all")
                .accept("application/json")
            )
            .andExpect(status().isOk())
            .andExpect(header().dateValue("Last-Modified", testDateEpochMilli))
            .andExpect(jsonPath("$[0].municipalDistrict").value("test"));

        verify(districtDataService, times(1)).getAllHealthWards();
    }

    @Test
    void getAllDistrictData_StatusIs304() throws Exception {
        //given
        HealthWard testDTO = new HealthWard();
        testDTO.setName("test");
        testDTO.setLastUpdated(testDate);
        List<HealthWard> testList = List.of(testDTO);

        //when
        when(districtDataService.getAllHealthWards()).thenReturn(testList);

        //verify
        mvc.perform(
                  get("/data/all")
                 .accept("application/json")
                 .header("If-Modified-Since", testDateEpochMilli)
            )
            .andExpect(status().is(304));

        verify(districtDataService, times(1)).getAllHealthWards();
    }

    @Test
    void getAllDistrictData_StatusIsNotFound() throws Exception {
        //when
        when(districtDataService.getAllHealthWards()).thenThrow(new DataNotFoundException("Data Not Found"));

        mvc.perform(
                get("/data/all")
                        .accept("application/json")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    void getDistrictDataByGeoCode_StatusIsOK() throws Exception {
        //given
        HealthWard testDTO = new HealthWard();
        testDTO.setGeoCode("geoCode");
        testDTO.setLastUpdated(testDate);

        //when
        when(districtDataService.getHealthWardByGeoCode("geoCode")).thenReturn(testDTO);

        //verify
        mvc.perform(
                 get("/data/district/geocode/geoCode")
                .accept("application/json")
            )
            .andExpect(status().isOk())
            .andExpect(header().dateValue("Last-Modified", testDateEpochMilli))
            .andExpect(jsonPath("$.geoCode").value("geoCode"));

        verify(districtDataService, times(1)).getHealthWardByGeoCode(anyString());
        verify(districtDataService, times(1)).getHealthWardByGeoCode("geoCode");
    }

    @Test
    void getDistrictDataByGeoCode_StatusIs304() throws Exception {
        //given
        HealthWard testDTO = new HealthWard();
        testDTO.setGeoCode("geoCode");
        testDTO.setLastUpdated(testDate);

        //when
        when(districtDataService.getHealthWardByGeoCode("geoCode")).thenReturn(testDTO);

        //verify
        mvc.perform(
                 get("/data/district/geocode/geoCode")
                .accept("application/json")
                .header("If-Modified-Since", testDateEpochMilli)
            )
            .andExpect(status().is(304));

        verify(districtDataService, times(1)).getHealthWardByGeoCode(anyString());
        verify(districtDataService, times(1)).getHealthWardByGeoCode("geoCode");
    }

    @Test
    void getDistrictDataByGeoCode_StatusIsNotFound() throws Exception {
        //when
        when(districtDataService.getHealthWardByGeoCode("null")).thenThrow(new DataNotFoundException("Data Not Found"));

        mvc.perform(
                get("/data/district/geocode/null")
                        .accept("application/json")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    void getDistrictDataByName_StatusIsOK() throws Exception {
        //given
        HealthWard testDTO = new HealthWard();
        testDTO.setName("name");
        testDTO.setLastUpdated(testDate);

        //when
        when(districtDataService.getHealthWardByName("name")).thenReturn(testDTO);

        //verify
        mvc.perform(
                     get("/data/district/name/name")
                    .accept("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(header().dateValue("Last-Modified", testDateEpochMilli))
                .andExpect(jsonPath("$.municipalDistrict").value("name"));

        verify(districtDataService, times(1)).getHealthWardByName(anyString());
        verify(districtDataService, times(1)).getHealthWardByName("name");
    }

    @Test
    void getDistrictDataByName_StatusIsNotFound() throws Exception {
        //when
        when(districtDataService.getHealthWardByName("null")).thenThrow(new DataNotFoundException("Data Not Found"));

        mvc.perform(
                get("/data/district/name/null")
                        .accept("application/json")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    void getDistrictDataByName_StatusIs304() throws Exception {
        //given
        HealthWard testDTO = new HealthWard();
        testDTO.setName("name");
        testDTO.setLastUpdated(testDate);

        //when
        when(districtDataService.getHealthWardByName("name")).thenReturn(testDTO);

        //verify
        mvc.perform(
                 get("/data/district/name/name")
                .accept("application/json")
                .header("If-Modified-Since", testDateEpochMilli)
        )
        .andExpect(status().is(304));

        verify(districtDataService, times(1)).getHealthWardByName(anyString());
        verify(districtDataService, times(1)).getHealthWardByName("name");
    }

    @Test
    void getDistrictNamesAndGeocodes() throws Exception {
        //given
        Map<String, String> namesAndGeocodes = new HashMap<>();
        namesAndGeocodes.put("one", "1");
        namesAndGeocodes.put("two", "2");
        namesAndGeocodes.put("three", "3");

        //when
        when(districtDataService.getHealthWardGeoCodesAndNames()).thenReturn(namesAndGeocodes);

        //verify
        mvc.perform(
                get("/data/names-geocodes")
                        .accept("application/json")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.one").value("1"))
                .andExpect(jsonPath("$.two").value("2"))
                .andExpect(jsonPath("$.three").value("3"));


        verify(districtDataService, times(1)).getHealthWardGeoCodesAndNames();

    }
}