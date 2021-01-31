package com.chilborne.covidradar.controllers;

import com.chilborne.covidradar.model.HealthWard;
import com.chilborne.covidradar.services.HealthWardService;
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
public class HealthWardController {

    private final HealthWardService healthWardService;
    private static Logger logger = LoggerFactory.getLogger(HealthWardController.class);

    public HealthWardController(HealthWardService healthWardService) {
        this.healthWardService = healthWardService;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<HealthWard>> getAllHealthWards(WebRequest request) {
        logger.debug("Processing Get Request --> All HealthWards");

        List<HealthWard> result = healthWardService.getAllHealthWards();
        if (hasBeenModifiedSince(result.get(0), request)) {
            return ResponseEntity.status(304).build();
        }
        return ResponseEntity.ok()
                .cacheControl(getCacheControlHealthWard())
                .lastModified(getEpochLastModified(result.get(0)))
                .body(result);
    }

    @GetMapping(value = "/geocode/{geoCode}", produces = "application/json")
    public ResponseEntity<HealthWard> getHealthWardByGeoCode(@PathVariable String geoCode, WebRequest request) {
        logger.debug("Processing Get Request --> geocode: " + geoCode);

        HealthWard result = healthWardService.getHealthWardByGeoCode(geoCode);
        if (hasBeenModifiedSince(result, request)) {
            return ResponseEntity.status(304).build();
        }
        return ResponseEntity.ok()
                .cacheControl(getCacheControlHealthWard())
                .lastModified(getEpochLastModified(result))
                .body(result);
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<HealthWard> getHealthWardByName(@PathVariable String name, WebRequest request) {
        logger.debug("Processing Get Request --> health ward: " + name);

        HealthWard result = healthWardService.getHealthWardByName(name);
        if (hasBeenModifiedSince(result, request)) {
            return ResponseEntity.status(304).build();
        }
        return ResponseEntity.ok()
                .cacheControl(getCacheControlHealthWard())
                .lastModified(getEpochLastModified(result))
                .body(result);
    }

    @GetMapping(value = "/names+geocodes", produces = "application/json")
    public ResponseEntity<Map<String, String>> getNamesAndGeoCodes() {
        logger.debug("Processing Get Request --> Names and GeoCodes");
        return ResponseEntity.ok()
                .cacheControl(getCacheControlNamesAndGeocodes())
                .body(healthWardService.getHealthWardGeoCodesAndNames());
    }

    private boolean hasBeenModifiedSince(HealthWard data, WebRequest request) {
        long lastModifiedEpochMilli = getEpochLastModified(data);
        return request.checkNotModified(lastModifiedEpochMilli);
    }

    private CacheControl getCacheControlHealthWard() {
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

    private long getEpochLastModified(HealthWard districtData) {
        LocalDate lastModified = districtData.getLastUpdated();
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        long epochLastModified = lastModified.atStartOfDay(zoneId).toInstant().toEpochMilli();
        return epochLastModified;
    }



}
