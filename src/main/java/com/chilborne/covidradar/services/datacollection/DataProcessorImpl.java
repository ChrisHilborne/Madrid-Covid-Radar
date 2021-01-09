package com.chilborne.covidradar.services.datacollection;

import com.chilborne.covidradar.model.DailyFigure;
import com.chilborne.covidradar.repository.DailyFigureRepositoryTest;
import com.chilborne.covidradar.services.DailyFigureService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Profile("test")
@Service
public class DataProcessorImpl implements DataProcessor {

    private final DailyFigureService dailyFigureService;

    public DataProcessorImpl(DailyFigureService dailyFigureService) {
        this.dailyFigureService = dailyFigureService;
    }

    @Override
    public void processData(String json) throws JsonProcessingException {
        List<DailyFigure> dailyFigureList = parseJsonDailyFigures(json);
        saveData(dailyFigureList);
    }

    private List<DailyFigure> parseJsonDailyFigures(String json) throws JsonProcessingException {
        List<DailyFigure> dailyFigureList;
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false)
                .registerModule(new JavaTimeModule());
        JsonNode listNode = mapper.readTree(json).path("data");
        dailyFigureList = Arrays.asList(mapper.treeToValue(listNode, DailyFigure[].class));
        return dailyFigureList;
    }

    private void saveData(List<DailyFigure> dailyFigureList) {
        Comparator<DailyFigure> geoCodeComparator = Comparator.comparing(DailyFigure::getGeoCode);
        Comparator<DailyFigure> dateComparator = Comparator.comparing(DailyFigure::getDateReported);
        Comparator<DailyFigure> combinedDateAndGeoCodeComparator = geoCodeComparator.thenComparing(dateComparator);

        dailyFigureList.stream()
                .filter(dailyFigure -> dailyFigure.getGeoCode().matches("07...."))
                .sorted(combinedDateAndGeoCodeComparator)
                .forEachOrdered(dailyFigureService::save);
    }
}
