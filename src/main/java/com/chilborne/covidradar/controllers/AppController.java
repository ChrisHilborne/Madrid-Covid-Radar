package com.chilborne.covidradar.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/")
public class AppController {

    private final Logger logger = LoggerFactory.getLogger(AppController.class);

    @GetMapping(value = "healthcheck")
    public ResponseEntity<String> healthCheck() {
        logger.debug("Health check request...");
        return ResponseEntity.ok().build();
    }

    @CacheEvict({
            "healthWard-all",
            "healthWard-geoCode",
            "namesAndGeoCodes"
    })
    @PostMapping(value = "clear-cache")
    public ResponseEntity<String> clearCache() {
        logger.debug("Clear cache request made");
        return ResponseEntity.ok().build();
    }

}
