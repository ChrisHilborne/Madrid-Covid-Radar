package com.chilborne.covidradar.model;

import java.time.LocalDate;

public class DailyFigureDTO {

    private String geoCode;
    private String district;
    private double twoWeekRate;
    private double totalRate;
    private int twoWeekCases;
    private int totalCases;
    private LocalDate date;

    public DailyFigureDTO(DailyFigure dailyFigure) {
        this.geoCode = dailyFigure.getGeoCode();
        this.district = dailyFigure.getMunicipalDistrict();
        this.totalCases = dailyFigure.getTotalCases();
        this.totalRate = dailyFigure.getInfectionRateTotal();
        this.twoWeekCases = dailyFigure.getCasesLastTwoWeeks();
        this.twoWeekRate = dailyFigure.getInfectionRateLastTwoWeeks();
        this.date = dailyFigure.getDateReported();
    }
    public DailyFigureDTO() {
    }

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public double getTwoWeekRate() {
        return twoWeekRate;
    }

    public void setTwoWeekRate(double twoWeekRate) {
        this.twoWeekRate = twoWeekRate;
    }

    public double getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(double totalRate) {
        this.totalRate = totalRate;
    }

    public int getTwoWeekCases() {
        return twoWeekCases;
    }

    public void setTwoWeekCases(int twoWeekCases) {
        this.twoWeekCases = twoWeekCases;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyFigureDTO that = (DailyFigureDTO) o;

        if (!geoCode.equals(that.geoCode)) return false;
        if (!district.equals(that.district)) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = geoCode.hashCode();
        result = 31 * result + district.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DailyFigureDTO{" +
                "geoCode='" + geoCode + '\'' +
                ", district='" + district + '\'' +
                ", twoWeekRate=" + twoWeekRate +
                ", totalRate=" + totalRate +
                ", twoWeekCases=" + twoWeekCases +
                ", totalCases=" + totalCases +
                ", date=" + date +
                '}';
    }
}
