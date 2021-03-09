package com.chilborne.covidradar.controllers;

import com.chilborne.covidradar.services.HealthWardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/")
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(AppController.class);
    
    private final HealthWardService healthWardService;

    public AppController(HealthWardService healthWardService) {
        this.healthWardService = healthWardService;
    }

    @GetMapping(value = "healthcheck")
    public ResponseEntity<String> healthCheck() {
        logger.debug("Health check request...");
        return ResponseEntity.ok().build();
    }

    @Caching(evict = {
            @CacheEvict(value = "healthWard-all", allEntries = true),
            @CacheEvict(value = "healthWard-geoCode", allEntries = true),
            @CacheEvict(value= "namesAndGeoCodes", allEntries = true)
    })
    @PostMapping(value = "clear-cache")
    public ResponseEntity<String> clearCache() {
        logger.debug("Clear cache request made");
        healthWardService.clearCache();
        return ResponseEntity.ok().build();
    }

}
