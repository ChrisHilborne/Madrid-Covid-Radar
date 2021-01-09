package com.chilborne.covidradar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * @Author Chris Hilborne
 * @Date 06.01.21
 */

@Document
public class DailyFigure {
    @Id
    private String _id;
    @JsonProperty("codigo_geometria")
    private String geoCode;
    @JsonProperty("municipio_distrito")
    private String municipalDistrict;
    @JsonProperty("tasa_incidencia_acumulada_ultimos_14dias")
    private float infectionRateLastTwoWeeks;
    @JsonProperty("tasa_incidencia_acumulada_total")
    private float infectionRateTotal;
    @JsonProperty("casos_confirmados_totales")
    private int totalCases;
    @JsonProperty("casos_confirmados_ultimos_14dias")
    private int casesLastTwoWeeks;
    @JsonProperty("fecha_informe")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDate dateReported;

    public DailyFigure() {}

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public LocalDate getDateReported() {
        return dateReported;
    }

    public void setDateReported(LocalDate dateReported) {
        this.dateReported = dateReported;
    }

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public String getMunicipalDistrict() {
        return municipalDistrict;
    }

    public void setMunicipalDistrict(String municipalDistrict) {
        this.municipalDistrict = municipalDistrict;
    }

    public float getInfectionRateLastTwoWeeks() {
        return infectionRateLastTwoWeeks;
    }

    public void setInfectionRateLastTwoWeeks(float infectionRateLastTwoWeeks) {
        this.infectionRateLastTwoWeeks = infectionRateLastTwoWeeks;
    }

    public float getInfectionRateTotal() {
        return infectionRateTotal;
    }

    public void setInfectionRateTotal(float infectionRateTotal) {
        this.infectionRateTotal = infectionRateTotal;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getCasesLastTwoWeeks() {
        return casesLastTwoWeeks;
    }

    public void setCasesLastTwoWeeks(int casesLastTwoWeeks) {
        this.casesLastTwoWeeks = casesLastTwoWeeks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyFigure that = (DailyFigure) o;

        if (!geoCode.equals(that.geoCode)) return false;
        return dateReported.equals(that.dateReported);
    }

    @Override
    public int hashCode() {
        int result = geoCode.hashCode();
        result = 31 * result + dateReported.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DailyFigure{" +
                "_id='" + _id + '\'' +
                ", geoCode='" + geoCode + '\'' +
                ", municipalDistrict='" + municipalDistrict + '\'' +
                ", infectionRateLastTwoWeeks=" + infectionRateLastTwoWeeks +
                ", infectionRateTotal=" + infectionRateTotal +
                ", totalCases=" + totalCases +
                ", casesLastTwoWeeks=" + casesLastTwoWeeks +
                ", date=" + dateReported +
                '}';
    }
}
