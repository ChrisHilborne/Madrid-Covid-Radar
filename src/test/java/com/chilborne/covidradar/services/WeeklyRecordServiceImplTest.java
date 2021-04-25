package com.chilborne.covidradar.services;

import com.chilborne.covidradar.cache.CacheService;
import com.chilborne.covidradar.exceptions.DataNotFoundException;
import com.chilborne.covidradar.exceptions.DataSaveException;
import com.chilborne.covidradar.model.DailyRecord;
import com.chilborne.covidradar.repository.DailyRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeeklyRecordServiceImplTest {

    @Mock
    DailyRecordRepository dailyRecordRepository;

    @Mock
    CacheService cacheService;

    @InjectMocks
    WeeklyRecordServiceImpl dailyRecordService;

    @Captor
    ArgumentCaptor<DailyRecord> dailyRecordArgumentCaptor;

    LocalDate testDate = LocalDate.now();

    @Test
    void save() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);

        //when
        when(dailyRecordRepository.save(test)).thenReturn(test);
        DailyRecord saved = dailyRecordService.save(test);

        //verify
        verify(dailyRecordRepository).save(dailyRecordArgumentCaptor.capture());
        assertAll("save",
                () -> assertNotEquals(null, dailyRecordArgumentCaptor.getValue().getId()),
                () -> assertEquals(test, dailyRecordArgumentCaptor.getValue()),
                () -> assertEquals("test", saved.getHealthWard()),
                () -> assertEquals(testDate, saved.getDateReported())
        );

    }

    @Test
    void save_Exception() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);

        //when
        when(dailyRecordRepository.save(test)).thenReturn(new DailyRecord());

        //verify
        Exception e = assertThrows(DataSaveException.class, () -> dailyRecordService.save(test));
        assertEquals("Failed to save DailyRecord id: " + test.getId(), e.getMessage());
    }


    @Test
    void saveList() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<DailyRecord> toSave = List.of(test);

        //when
        when(dailyRecordRepository.save(test)).thenReturn(test);
        List<DailyRecord> returned = dailyRecordService.save(toSave);

        //verify
        verify(dailyRecordRepository).save(dailyRecordArgumentCaptor.capture());
        assertEquals(test, dailyRecordArgumentCaptor.getValue());
        assertAll("saveList",
                () -> assertEquals(1, returned.size()),
                () -> assertEquals("test", returned.get(0).getHealthWard()),
                () -> assertEquals(testDate, returned.get(0).getDateReported())
        );
        verify(cacheService, times(1)).clearCache();
    }

    @Test
    void saveList_Exception() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<DailyRecord> toSave = List.of(test);

        //when
        when(dailyRecordRepository.save(test)).thenReturn(new DailyRecord());

        //verify
        Exception e = assertThrows(DataSaveException.class, () -> dailyRecordService.save(toSave));
        assertEquals("Error when saving dailyRecordList (hashcode " + toSave.hashCode() + ")", e.getMessage());
    }

    @Test
    void getAll() {
        //given
        DailyRecord test = new DailyRecord();
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<DailyRecord> toGet = List.of(test);

        //when
        when(dailyRecordRepository.findAll()).thenReturn(toGet);
        List<DailyRecord> returned = dailyRecordService.getAll();

        //verify
        assertAll("getAll",
                () -> assertEquals(1, returned.size()),
                () -> assertEquals("test", returned.get(0).getHealthWard()),
                () -> assertEquals(testDate, returned.get(0).getDateReported())
        );
    }

    @Test
    void getAll_Exception() {
        //when
        when(dailyRecordRepository.findAll()).thenReturn(List.of());

        //verify
        Exception e = assertThrows(DataNotFoundException.class, () -> dailyRecordService.getAll());
        assertEquals("No Daily Records found.", e.getMessage());
    }



    @Test
    void getDailyRecordsByGeoCode() {
        //given
        DailyRecord test = new DailyRecord();
        test.setGeoCode("01");
        test.setHealthWard("test");
        test.setDateReported(testDate);
        List<DailyRecord> toGet = List.of(test);

        //when
        when(dailyRecordRepository.findByGeoCode("01")).thenReturn(toGet);
        List<DailyRecord> returned = dailyRecordService.getDailyRecordsByGeoCode("01");

        //verify
        assertAll("getByHealthWard",
                () -> assertEquals(1, returned.size()),
                () -> assertEquals("01", returned.get(0).getGeoCode()),
                () -> assertEquals("test", returned.get(0).getHealthWard()),
                () -> assertEquals(testDate, returned.get(0).getDateReported())
        );
    }

    @Test
    void getDailyRecordByGeoCode_Exception() {
            //when
            when(dailyRecordRepository.findByGeoCode("test")).thenReturn(List.of());

            //verify
            Exception e = assertThrows(DataNotFoundException.class, () -> dailyRecordService.getDailyRecordsByGeoCode("test"));
            assertEquals("No data found for geocode: test" , e.getMessage());
    }

    @Test
    void getDailyRecordByGeoCodeAndDate() {
        //given
        DailyRecord test = new DailyRecord();
        test.setGeoCode("01");
        test.setHealthWard("test");
        test.setDateReported(testDate);

        //when
        when(dailyRecordRepository.findByGeoCodeAndDateReported("01", testDate)).thenReturn(Optional.of(test));
        DailyRecord returned = dailyRecordService.getDailyRecordByGeoCodeAndDate("01", testDate);

        //verify
        assertAll("getByGeoCodeAndDate",
                () -> assertEquals("01", returned.getGeoCode()),
                () -> assertEquals("test", returned.getHealthWard()),
                () -> assertEquals(testDate, returned.getDateReported())
        );
    }

    @Test
    void getDailyRecordByGeoCodeAndDate_Exception() {
        //when
        when(dailyRecordRepository.findByGeoCodeAndDateReported("01", testDate)).thenReturn(Optional.empty());

        //verify
        Exception e = assertThrows(DataNotFoundException.class, () -> dailyRecordService.getDailyRecordByGeoCodeAndDate("01", testDate));
        assertEquals("No data found for geocode: 01 and dateReported: " + testDate.toString(), e.getMessage());
    }

    @Test
    void getNamesAndGeoCodes() {
        //given
        LocalDate firstDateOfResults = LocalDate.of(2020, 3 ,3);

        DailyRecord one = new DailyRecord();
        one.setGeoCode("01");
        one.setHealthWard("b");
        DailyRecord two = new DailyRecord();
        two.setGeoCode("02");
        two.setHealthWard("a");
        DailyRecord three = new DailyRecord();
        three.setGeoCode("03");
        three.setHealthWard("c");
        List<DailyRecord> dailyRecordList = List.of(one, two, three);

        Map<String, String> expectedResults = new LinkedHashMap<>();
        expectedResults.put("b", "01");
        expectedResults.put("a", "02");
        expectedResults.put("c", "03");

        //when
        when(dailyRecordRepository.findByDateReported(firstDateOfResults)).thenReturn(dailyRecordList);
        Map<String, String> results = dailyRecordService.getNamesAndGeoCodes();

        //verify
        verify(dailyRecordRepository, times(1)).findByDateReported(firstDateOfResults);
        verify(dailyRecordRepository, times(1)).findByDateReported(any());
        assertEquals(expectedResults, results);
    }
}