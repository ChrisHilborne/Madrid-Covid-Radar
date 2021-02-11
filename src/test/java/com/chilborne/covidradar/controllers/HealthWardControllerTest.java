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
class HealthWardControllerTest {

    MockMvc mvc;

    LocalDate testDate;

    long testDateEpochMilli;

    @Mock
    HealthWardService healthWardServiceService;

    @InjectMocks
    HealthWardController healthWardControllerController;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(healthWardControllerController).build();

        testDate = LocalDate.now();
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        testDateEpochMilli = testDate.atStartOfDay(zoneId).toInstant().toEpochMilli();
    }

    @Test
    void getAllHealthWards_StatusIsOK() throws Exception {
        //given
        HealthWard testHealthWard = new HealthWard();
        testHealthWard.setName("test");
        testHealthWard.setLastUpdated(testDate);
        List<HealthWard> testList = List.of(testHealthWard);

        //when
        when(healthWardServiceService.getAllHealthWards()).thenReturn(testList);

        //verify
        mvc.perform(
                 get("/api/all")
                .accept("application/json")
            )
            .andExpect(status().isOk())
            .andExpect(header().dateValue("Last-Modified", testDateEpochMilli))
            .andExpect(jsonPath("$[0].name").value("test"));

        verify(healthWardServiceService, times(1)).getAllHealthWards();
    }

    @Test
    void getAllHealthWard_StatusIs304() throws Exception {
        //given
        HealthWard testHealthWard = new HealthWard();
        testHealthWard.setName("test");
        testHealthWard.setLastUpdated(testDate);
        List<HealthWard> testList = List.of(testHealthWard);

        //when
        when(healthWardServiceService.getAllHealthWards()).thenReturn(testList);

        //verify
        mvc.perform(
                  get("/api/all")
                 .accept("application/json")
                 .header("If-Modified-Since", testDateEpochMilli)
            )
            .andExpect(status().is(304));

        verify(healthWardServiceService, times(1)).getAllHealthWards();
    }

    @Test
    void getAllHealthWard_StatusIsNotFound() throws Exception {
        //when
        when(healthWardServiceService.getAllHealthWards()).thenThrow(new DataNotFoundException("Data Not Found"));

        mvc.perform(
                get("/api/all")
                        .accept("application/json")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    void getHealthWardByGeoCode_StatusIsOK() throws Exception {
        //given
        HealthWard testHealthWard = new HealthWard();
        testHealthWard.setGeoCode("geoCode");
        testHealthWard.setLastUpdated(testDate);

        //when
        when(healthWardServiceService.getHealthWardByGeoCode("geoCode")).thenReturn(testHealthWard);

        //verify
        mvc.perform(
                 get("/api/geocode/geoCode")
                .accept("application/json")
            )
            .andExpect(status().isOk())
            .andExpect(header().dateValue("Last-Modified", testDateEpochMilli))
            .andExpect(jsonPath("$.geoCode").value("geoCode"));

        verify(healthWardServiceService, times(1)).getHealthWardByGeoCode(anyString());
        verify(healthWardServiceService, times(1)).getHealthWardByGeoCode("geoCode");
    }

    @Test
    void getHealthWardByGeoCode_StatusIs304() throws Exception {
        //given
        HealthWard testHealthWard = new HealthWard();
        testHealthWard.setGeoCode("geoCode");
        testHealthWard.setLastUpdated(testDate);

        //when
        when(healthWardServiceService.getHealthWardByGeoCode("geoCode")).thenReturn(testHealthWard);

        //verify
        mvc.perform(
                 get("/api/geocode/geoCode")
                .accept("application/json")
                .header("If-Modified-Since", testDateEpochMilli)
            )
            .andExpect(status().is(304));

        verify(healthWardServiceService, times(1)).getHealthWardByGeoCode(anyString());
        verify(healthWardServiceService, times(1)).getHealthWardByGeoCode("geoCode");
    }

    @Test
    void getHealthWardByGeoCode_StatusIsNotFound() throws Exception {
        //when
        when(healthWardServiceService.getHealthWardByGeoCode("null")).thenThrow(new DataNotFoundException("Data Not Found"));

        mvc.perform(
                get("/api/geocode/null")
                        .accept("application/json")
        )
                .andExpect(status().isNotFound());
    }


    @Test
    void getDistrictNamesAndGeocodes() throws Exception {
        //given
        Map<String, String> namesAndGeocodes = new HashMap<>();
        namesAndGeocodes.put("one", "1");
        namesAndGeocodes.put("two", "2");
        namesAndGeocodes.put("three", "3");

        //when
        when(healthWardServiceService.getHealthWardGeoCodesAndNames()).thenReturn(namesAndGeocodes);

        //verify
        mvc.perform(
                get("/api/names&geocodes")
                        .accept("application/json")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.one").value("1"))
                .andExpect(jsonPath("$.two").value("2"))
                .andExpect(jsonPath("$.three").value("3"));


        verify(healthWardServiceService, times(1)).getHealthWardGeoCodesAndNames();

    }
}