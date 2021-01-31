package com.chilborne.covidradar.controllers;

import com.chilborne.covidradar.model.DistrictData;
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
import java.util.Map;
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

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<DistrictData>> getAllDistrictData(WebRequest request) {
        logger.debug("Processing Get Request --> All District Data");

        List<DistrictData> result = districtDataService.getAllDistrictData();
        if (hasBeenModifiedSince(result.get(0), request)) {
            return ResponseEntity.status(304).build();
        }
        return ResponseEntity.ok()
                .cacheControl(getCacheControlDistrictData())
                .lastModified(getEpochLastModified(result.get(0)))
                .body(result);
    }

    @GetMapping(value = "/geocode/{geoCode}", produces = "application/json")
    public ResponseEntity<DistrictData> getDistrictDataByGeoCode(@PathVariable String geoCode, WebRequest request) {
        logger.debug("Processing Get Request --> geocode: " + geoCode);

        DistrictData result = districtDataService.getDistrictDataByGeoCode(geoCode);
        if (hasBeenModifiedSince(result, request)) {
            return ResponseEntity.status(304).build();
        }
        return ResponseEntity.ok()
                .cacheControl(getCacheControlDistrictData())
                .lastModified(getEpochLastModified(result))
                .body(result);
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<DistrictData> getDistrictDataByName(@PathVariable String name, WebRequest request) {
        logger.debug("Processing Get Request --> district: " + name);

        DistrictData result = districtDataService.getDistrictDataByName(name);
        if (hasBeenModifiedSince(result, request)) {
            return ResponseEntity.status(304).build();
        }
        return ResponseEntity.ok()
                .cacheControl(getCacheControlDistrictData())
                .lastModified(getEpochLastModified(result))
                .body(result);
    }

    @GetMapping(value = "/names+geocodes", produces = "application/json")
    public ResponseEntity<Map<String, String>> getNamesAndGeocodes() {
        logger.debug("Processing Get Request --> Names and Geocodes");
        return ResponseEntity.ok()
                .cacheControl(getCacheControlNamesAndGeocodes())
                .body(districtDataService.getDistrictGeoCodesAndNames());
    }

    private boolean hasBeenModifiedSince(DistrictData dataDTO, WebRequest request) {
        long lastModifiedEpochMilli = getEpochLastModified(dataDTO);
        return request.checkNotModified(lastModifiedEpochMilli);
    }

    private CacheControl getCacheControlDistrictData() {
        CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS)
                .mustRevalidate()
                .noTransform();
        return cacheControl;
    }

    private CacheControl getCacheControlNamesAndGeocodes() {
        return CacheControl.maxAge(7, TimeUnit.DAYS)
                .noTransform()
                .mustRevalidate();
    }

    private long getEpochLastModified(DistrictData districtData) {
        LocalDate lastModified = districtData.getLastUpdated();
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        long epochLastModified = lastModified.atStartOfDay(zoneId).toInstant().toEpochMilli();
        return epochLastModified;
    }



}
