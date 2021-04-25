package com.chilborne.covidradar.model;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class HealthWard {

    @NotBlank
    @Size(max = 100)
    private String geoCode;
    @NotBlank
    @Size(max = 100)
    private String name;
    @PositiveOrZero
    private int totalCases;
    @Past
    private LocalDate lastUpdated;
    private int recordCount;
    @NotEmpty
    private List<WeeklyRecordDTO> WeeklyRecords;

    public HealthWard(List<WeeklyRecord> weeklyRecords) {
        WeeklyRecord lastRecord = weeklyRecords.get(weeklyRecords.size() - 1);
        this.geoCode = lastRecord.getGeoCode();
        this.name = lastRecord.getHealthWard();
        this.totalCases = lastRecord.getTotalCases();
        this.lastUpdated = lastRecord.getDateReported();
        this.recordCount = weeklyRecords.size();
        this.WeeklyRecords = weeklyRecords
                .stream()
                .map(WeeklyRecordDTO::new)
                .collect(Collectors.toList());
    }

    public HealthWard() {
    }

    public String getName() { return name; }

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public List<WeeklyRecordDTO> getWeeklyRecords() { return WeeklyRecords; }

    public void setWeeklyRecords(List<WeeklyRecordDTO> weeklyRecords) { this.WeeklyRecords = weeklyRecords; }

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

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HealthWard that = (HealthWard) o;

        if (totalCases != that.totalCases) return false;
        if (!Objects.equals(geoCode, that.geoCode)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(lastUpdated, that.lastUpdated)) return false;
        return Objects.equals(WeeklyRecords, that.WeeklyRecords);
    }

    @Override
    public int hashCode() {
        int result = geoCode != null ? geoCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + totalCases;
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (WeeklyRecords != null ? WeeklyRecords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HealthWard{" +
                "geoCode='" + geoCode + '\'' +
                ", name='" + name + '\'' +
                ", totalCases=" + totalCases +
                ", lastUpdated=" + lastUpdated +
                ", weeklyRecords=" + WeeklyRecords +
                '}';
    }
}
