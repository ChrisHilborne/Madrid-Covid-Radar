package com.chilborne.covidradar.services.datacollection;

import com.chilborne.covidradar.model.ComunidadDailyData;
import com.chilborne.covidradar.model.DailyFigure;
import com.chilborne.covidradar.repository.DailyFigureRepositoryTest;
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
public class DataProcessorTest implements DataProcessor {

    private final DailyFigureRepositoryTest dailyFigureRepository;

    public DataProcessorTest(DailyFigureRepositoryTest dailyFigureRepository) {
        this.dailyFigureRepository = dailyFigureRepository;
    }

    @Override
    public void processData(String json) throws JsonProcessingException {
        List<ComunidadDailyData> comunidadDailyDataList = parseJsonComunidadDailyData(json);

        transformData(comunidadDailyDataList);

    }

    private List<ComunidadDailyData> parseJsonComunidadDailyData(String json) throws JsonProcessingException {
        List<ComunidadDailyData> comunidadDailyDataList;
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false)
                .registerModule(new JavaTimeModule());
        JsonNode listNode = mapper.readTree(json).path("data");
        comunidadDailyDataList = Arrays.asList(mapper.treeToValue(listNode, ComunidadDailyData[].class));
        return comunidadDailyDataList;
    }

    private void transformData(List<ComunidadDailyData> comunidadDailyDataList) {
        Comparator<DailyFigure> geoCodeComparator = Comparator.comparing(DailyFigure::getGeometricCode);
        Comparator<DailyFigure> dateComparator = Comparator.comparing(DailyFigure::getDate);
        Comparator<DailyFigure> combinedDateAndGeoCodeComparator = geoCodeComparator.thenComparing(dateComparator);

        comunidadDailyDataList.stream()
                .filter(communidadData -> communidadData.getCodigoGeometria().startsWith("07"))
                .map(comunidadData -> new DailyFigure(comunidadData))
                .sorted(combinedDateAndGeoCodeComparator)
                .forEachOrdered(dailyFigureRepository::add);
    }
}
