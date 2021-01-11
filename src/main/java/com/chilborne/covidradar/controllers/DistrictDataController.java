package com.chilborne.covidradar.controllers;

import com.chilborne.covidradar.model.DistrictDataDTO;
import com.chilborne.covidradar.services.DistrictDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<DistrictDataDTO> getAllDistrictData() {
        logger.debug("Processing Get Request --> All District Data");
        return districtDataService.getAllDistrictData();
    }

    @GetMapping(value = "/districts/names", produces = "application/json")
    public List<String> getDistrictNames() {
        logger.debug("Processing GetRequest --> District Names");
        return districtDataService.getDistrictNames();
    }

    @GetMapping(value = "/districts/{name}", produces = "application/json")
    public DistrictDataDTO getDistrictDataByName(@PathVariable String name) {
        logger.debug("Processing GetRequest --> " + name + " Data");
        return districtDataService.getDistrictData(name);
    }






}
