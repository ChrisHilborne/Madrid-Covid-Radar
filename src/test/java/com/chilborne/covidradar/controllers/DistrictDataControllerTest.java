package com.chilborne.covidradar.controllers;

import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.model.DistrictDataDTO;
import com.chilborne.covidradar.services.DistrictDataService;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DistrictDataControllerTest {


    MockMvc mvc;

    LocalDate testDate;

    long testDateEpochMilli;

    @Mock
    DistrictDataService districtDataService;

    @InjectMocks
    DistrictDataController districtDataController;

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
        DistrictDataDTO testDTO = new DistrictDataDTO();
        testDTO.setMunicipalDistrict("test");
        testDTO.setLastReported(testDate);
        List<DistrictDataDTO> testList = List.of(testDTO);

        //when
        when(districtDataService.getAllDistrictData()).thenReturn(testList);

        //verify
        mvc.perform(
                 get("/api/districts")
                .accept("application/json")
            )
            .andExpect(status().isOk())
            .andExpect(header().dateValue("Last-Modified", testDateEpochMilli))
            .andExpect(jsonPath("$[0].municipalDistrict").value("test"));

        verify(districtDataService, times(1)).getAllDistrictData();
    }

    @Test
    void getAllDistrictData_StatusIs304() throws Exception {
        //given
        DistrictDataDTO testDTO = new DistrictDataDTO();
        testDTO.setMunicipalDistrict("test");
        testDTO.setLastReported(testDate);
        List<DistrictDataDTO> testList = List.of(testDTO);

        //when
        when(districtDataService.getAllDistrictData()).thenReturn(testList);

        //verify
        mvc.perform(
                  get("/api/districts")
                 .accept("application/json")
                 .header("If-Modified-Since", testDateEpochMilli)
            )
            .andExpect(status().is(304));

        verify(districtDataService, times(1)).getAllDistrictData();
    }

    @Test
    void getAllDistrictData_StatusIsNotFound() throws Exception {
        //when
        when(districtDataService.getAllDistrictData()).thenThrow(new DataNotFoundException("Data Not Found"));

        mvc.perform(
                get("/api/districts")
                        .accept("application/json")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    void getDistrictDataByGeoCode_StatusIsOK() throws Exception {
        //given
        DistrictDataDTO testDTO = new DistrictDataDTO();
        testDTO.setGeoCode("geoCode");
        testDTO.setLastReported(testDate);

        //when
        when(districtDataService.getDistrictDataByGeoCode("geoCode")).thenReturn(testDTO);

        //verify
        mvc.perform(
                 get("/api/districts/geocode/geoCode")
                .accept("application/json")
            )
            .andExpect(status().isOk())
            .andExpect(header().dateValue("Last-Modified", testDateEpochMilli))
            .andExpect(jsonPath("$.geoCode").value("geoCode"));

        verify(districtDataService, times(1)).getDistrictDataByGeoCode(anyString());
        verify(districtDataService, times(1)).getDistrictDataByGeoCode("geoCode");
    }

    @Test
    void getDistrictDataByGeoCode_StatusIs304() throws Exception {
        //given
        DistrictDataDTO testDTO = new DistrictDataDTO();
        testDTO.setGeoCode("geoCode");
        testDTO.setLastReported(testDate);

        //when
        when(districtDataService.getDistrictDataByGeoCode("geoCode")).thenReturn(testDTO);

        //verify
        mvc.perform(
                 get("/api/districts/geocode/geoCode")
                .accept("application/json")
                .header("If-Modified-Since", testDateEpochMilli)
            )
            .andExpect(status().is(304));

        verify(districtDataService, times(1)).getDistrictDataByGeoCode(anyString());
        verify(districtDataService, times(1)).getDistrictDataByGeoCode("geoCode");
    }

    @Test
    void getDistrictDataByGeoCode_StatusIsNotFound() throws Exception {
        //when
        when(districtDataService.getDistrictDataByGeoCode("null")).thenThrow(new DataNotFoundException("Data Not Found"));

        mvc.perform(
                get("/api/districts/geocode/null")
                        .accept("application/json")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    void getDistrictDataByName_StatusIsOK() throws Exception {
        //given
        DistrictDataDTO testDTO = new DistrictDataDTO();
        testDTO.setMunicipalDistrict("name");
        testDTO.setLastReported(testDate);

        //when
        when(districtDataService.getDistrictDataByName("name")).thenReturn(testDTO);

        //verify
        mvc.perform(
                     get("/api/districts/name/name")
                    .accept("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(header().dateValue("Last-Modified", testDateEpochMilli))
                .andExpect(jsonPath("$.municipalDistrict").value("name"));

        verify(districtDataService, times(1)).getDistrictDataByName(anyString());
        verify(districtDataService, times(1)).getDistrictDataByName("name");
    }

    @Test
    void getDistrictDataByName_StatusIsNotFound() throws Exception {
        //when
        when(districtDataService.getDistrictDataByName("null")).thenThrow(new DataNotFoundException("Data Not Found"));

        mvc.perform(
                get("/api/districts/name/null")
                        .accept("application/json")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    void getDistrictDataByName_StatusIs304() throws Exception {
        //given
        DistrictDataDTO testDTO = new DistrictDataDTO();
        testDTO.setMunicipalDistrict("name");
        testDTO.setLastReported(testDate);

        //when
        when(districtDataService.getDistrictDataByName("name")).thenReturn(testDTO);

        //verify
        mvc.perform(
                 get("/api/districts/name/name")
                .accept("application/json")
                .header("If-Modified-Since", testDateEpochMilli)
        )
        .andExpect(status().is(304));

        verify(districtDataService, times(1)).getDistrictDataByName(anyString());
        verify(districtDataService, times(1)).getDistrictDataByName("name");
    }

    @Test
    void getDistrictNames() throws Exception {
        //given
        List<String> nameList = List.of("name-one", "name-two", "name-three");

        //when
        when(districtDataService.getDistrictNames()).thenReturn(nameList);

        //verify
        mvc.perform(
                get("/api/districts/names")
                .accept("application/json")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0]").value("name-one"))
            .andExpect(jsonPath("$.[1]").value("name-two"))
            .andExpect(jsonPath("$.[2]").value("name-three"));

        verify(districtDataService, times(1)).getDistrictNames();

    }
}