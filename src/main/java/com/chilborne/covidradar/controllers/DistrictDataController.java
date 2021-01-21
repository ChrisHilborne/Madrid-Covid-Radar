package com.chilborne.covidradar.controllers;

import com.chilborne.covidradar.model.DistrictDataDTO;
import com.chilborne.covidradar.services.DistrictDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DistrictDataController {

    private final DistrictDataService districtDataService;
    private static Logger logger = LoggerFactory.getLogger(DistrictDataController.class);

    public DistrictDataController(DistrictDataService districtDataService) {
        this.districtDataService = districtDataService;
    }

    @GetMapping(value = "/districts", produces = "application/json")
    public ResponseEntity<List<DistrictDataDTO>> getAllDistrictData(WebRequest request) {
        logger.debug("Processing Get Request --> All District Data");
        List<DistrictDataDTO> result = districtDataService.getAllDistrictData();
        long lastModifiedEpochMilli = getEpochLastModified(result.get(0));

        if (request.checkNotModified(lastModifiedEpochMilli)) {
            return ResponseEntity.status(304).build();
        }
        return ResponseEntity.ok()
                .cacheControl(getCacheControl())
                .lastModified(lastModifiedEpochMilli)
                .body(result);
    }

    @GetMapping(value = "/districts/geocode/{geoCode}", produces = "application/json")
    public ResponseEntity<DistrictDataDTO> getDistrictDataByGeoCode(@PathVariable String geoCode, WebRequest request) {
        logger.debug("Processing GetRequest --> " + geoCode + " DistrictData");
        DistrictDataDTO result = districtDataService.getDistrictDataByGeoCode(geoCode);

        long lastModifiedEpochMilli = getEpochLastModified(result);
        if (request.checkNotModified(lastModifiedEpochMilli)) {
            return ResponseEntity.status(304).build();
        }
        return ResponseEntity.ok()
                .cacheControl(getCacheControl())
                .lastModified(lastModifiedEpochMilli)
                .body(result);
    }

    @GetMapping(value = "/districts/name/{name}", produces = "application/json")
    public ResponseEntity<DistrictDataDTO> getDistrictDataByName(@PathVariable String name, WebRequest request) {
        logger.debug("Processing GetRequest --> " + name + " DistrictData");
        DistrictDataDTO result = districtDataService.getDistrictDataByName(name);
        long lastModifiedEpochMilli = getEpochLastModified(result);

        if (request.checkNotModified(lastModifiedEpochMilli)) {
            return ResponseEntity.status(304).build();
        }
        return ResponseEntity.ok()
                .cacheControl(getCacheControl())
                .lastModified(lastModifiedEpochMilli)
                .body(result);
    }

    @GetMapping(value = "/districts/names", produces = "application/json")
    public ResponseEntity<List<String>> getDistrictNames() {
        logger.debug("Processing GetRequest --> District Names");
        CacheControl cacheControl = CacheControl.maxAge(7, TimeUnit.DAYS)
                .noTransform()
                .mustRevalidate();
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(districtDataService.getDistrictNames());
    }


    private CacheControl getCacheControl() {
        CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS)
                .mustRevalidate()
                .noTransform();
        return cacheControl;
    }

    private long getEpochLastModified(DistrictDataDTO districtData) {
        LocalDate lastModified = districtData.getLastReported();
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        long epochLastModified = lastModified.atStartOfDay(zoneId).toInstant().toEpochMilli();
        return epochLastModified;
    }



}
