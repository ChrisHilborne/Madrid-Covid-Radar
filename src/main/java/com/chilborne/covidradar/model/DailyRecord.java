package com.chilborne.covidradar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @Author Chris Hilborne
 * @Date 06.01.21
 */
@Document(collection = "daily_records")
public class DailyRecord {

    @Id
    private String id;
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

    public DailyRecord() {}

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public LocalDate getDateReported() {
        return dateReported;
    }

    public void setDateReported(LocalDate dateReported) {
        this.dateReported = dateReported;
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

        DailyRecord that = (DailyRecord) o;

        if (Float.compare(that.infectionRateLastTwoWeeks, infectionRateLastTwoWeeks) != 0) return false;
        if (Float.compare(that.infectionRateTotal, infectionRateTotal) != 0) return false;
        if (totalCases != that.totalCases) return false;
        if (casesLastTwoWeeks != that.casesLastTwoWeeks) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(geoCode, that.geoCode)) return false;
        if (!Objects.equals(municipalDistrict, that.municipalDistrict))
            return false;
        return Objects.equals(dateReported, that.dateReported);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (geoCode != null ? geoCode.hashCode() : 0);
        result = 31 * result + (municipalDistrict != null ? municipalDistrict.hashCode() : 0);
        result = 31 * result + (infectionRateLastTwoWeeks != +0.0f ? Float.floatToIntBits(infectionRateLastTwoWeeks) : 0);
        result = 31 * result + (infectionRateTotal != +0.0f ? Float.floatToIntBits(infectionRateTotal) : 0);
        result = 31 * result + totalCases;
        result = 31 * result + casesLastTwoWeeks;
        result = 31 * result + (dateReported != null ? dateReported.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DailyFigure{" +
                ", municipalDistrict='" + municipalDistrict + '\'' +
                ", infectionRateLastTwoWeeks=" + infectionRateLastTwoWeeks +
                ", infectionRateTotal=" + infectionRateTotal +
                ", totalCases=" + totalCases +
                ", casesLastTwoWeeks=" + casesLastTwoWeeks +
                ", date=" + dateReported +
                '}';
    }
}
