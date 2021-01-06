package com.chilborne.covidradar.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author Chris Hilborne
 * @Date 06.01.21
 */

@Document
public class DailyFigure {
    @Id
    private String _id;
    private LocalDateTime date;
    private String geoCode;
    private int casesLastTwoWeeks;
    private int totalCases;

    public DailyFigure(ComunidadDailyData comunidadDailyData) {
        this.date = comunidadDailyData.getFechaInforme();
        this.geoCode = comunidadDailyData.getCodigoGeometria();
        this.casesLastTwoWeeks = comunidadDailyData.getCasosConfirmadosUltimos14dias();
        this.totalCases = comunidadDailyData.getCasosConfirmadosTotales();
    }

    public DailyFigure() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getGeometricCode() {
        return geoCode;
    }

    public void setGeometricCode(String geometricCode) {
        this.geoCode = geometricCode;
    }

    public int getCasesLastTwoWeeks() {
        return casesLastTwoWeeks;
    }

    public void setCasesLastTwoWeeks(int casesLastTwoWeeks) {
        this.casesLastTwoWeeks = casesLastTwoWeeks;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyFigure that = (DailyFigure) o;

        if (casesLastTwoWeeks != that.casesLastTwoWeeks) return false;
        if (totalCases != that.totalCases) return false;
        if (!_id.equals(that._id)) return false;
        if (!Objects.equals(date, that.date)) return false;
        return Objects.equals(geoCode, that.geoCode);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (geoCode != null ? geoCode.hashCode() : 0);
        result = 31 * result + casesLastTwoWeeks;
        result = 31 * result + totalCases;
        return result;
    }

    @Override
    public String toString() {
        return "DailyFigure{" +
                "_id='" + _id + '\'' +
                ", date=" + date +
                ", geoCode='" + geoCode + '\'' +
                ", casesLastTwoWeeks=" + casesLastTwoWeeks +
                ", totalCases=" + totalCases +
                '}';
    }
}
