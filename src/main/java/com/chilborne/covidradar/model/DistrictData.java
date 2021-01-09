package com.chilborne.covidradar.model;

import java.time.LocalDate;
import java.util.List;

public class DistrictData {

    private String name;
    private int totalCases;
    private LocalDate lastUpdated;
    private List<DailyFigureDTO> dailyFigures;

    public DistrictData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<DailyFigureDTO> getDailyFigures() {
        return dailyFigures;
    }

    public void setDailyFigures(List<DailyFigureDTO> dailyFigures) {
        this.dailyFigures = dailyFigures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistrictData that = (DistrictData) o;

        if (totalCases != that.totalCases) return false;
        if (!name.equals(that.name)) return false;
        return dailyFigures.equals(that.dailyFigures);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + totalCases;
        result = 31 * result + dailyFigures.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DistrictData{" +
                "name='" + name + '\'' + "\n" +
                ", totalCases=" + totalCases + "\n" +
                ", lastUpdated=" + lastUpdated + "\n" +
                '}';
    }
}
