package com.chilborne.covidradar.data.steps;

import com.chilborne.covidradar.model.DistrictData;
import com.chilborne.covidradar.services.DistrictDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistrictDataSaver implements Step<List<DistrictData>, List<DistrictData>>{

    private final DistrictDataService districtDataService;

    private final Logger logger = LoggerFactory.getLogger(DistrictData.class);

    @Autowired
    public DistrictDataSaver(DistrictDataService districtDataService) {
        this.districtDataService = districtDataService;
    }


    @Override
    public List<DistrictData> process(List<DistrictData> input) throws PipeLineProcessException {
        logger.debug("Starting to save DistrictData " + input.hashCode() + " (hashcode)...");

        List<DistrictData> output = districtDataService.save(input);

        if (!output.equals(input)) {
            logger.error("DistrictData not saved correctly.", new PipeLineProcessException());
        }

        return output;
    }
}
