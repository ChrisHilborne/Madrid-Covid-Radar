package com.chilborne.covidradar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * @Author Chris Hilborne
 * @Date 06.01.21
 */

public class ComunidadDailyData {


    @JsonProperty("codigo_geometria")
    private String codigoGeometria;
    @JsonProperty("municipio_distrito")
    private String municipoDistrito;
    @JsonProperty("tasa_incidencia_acumulada_ultimos_14dias")
    private float tasaIncidenciaAcumuladaUltimos14dias;
    @JsonProperty("tasa_incidencia_acumulada_total")
    private float tasaIncidenciaAcumuladaTotal;
    @JsonProperty("casos_confirmados_totales")
    private int casosConfirmadosTotales;
    @JsonProperty("casos_confirmados_ultimos_14dias")
    private int casosConfirmadosUltimos14dias;
    @JsonProperty("fecha_informe")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime fechaInforme;

    public ComunidadDailyData() {
    }

    public String getCodigoGeometria() {
        return codigoGeometria;
    }

    public void setCodigoGeometria(String codigoGeometria) {
        this.codigoGeometria = codigoGeometria;
    }

    public String getMunicipoDistrito() {
        return municipoDistrito;
    }

    public void setMunicipoDistrito(String municipoDistrito) {
        this.municipoDistrito = municipoDistrito;
    }

    public float getTasaIncidenciaAcumuladaUltimos14dias() {
        return tasaIncidenciaAcumuladaUltimos14dias;
    }

    public void setTasaIncidenciaAcumuladaUltimos14dias(float tasaIncidenciaAcumuladaUltimos14dias) {
        this.tasaIncidenciaAcumuladaUltimos14dias = tasaIncidenciaAcumuladaUltimos14dias;
    }

    public float getTasaIncidenciaAcumuladaTotal() {
        return tasaIncidenciaAcumuladaTotal;
    }

    public void setTasaIncidenciaAcumuladaTotal(float tasaIncidenciaAcumuladaTotal) {
        this.tasaIncidenciaAcumuladaTotal = tasaIncidenciaAcumuladaTotal;
    }

    public int getCasosConfirmadosTotales() {
        return casosConfirmadosTotales;
    }

    public void setCasosConfirmadosTotales(int casosConfirmadosTotales) {
        this.casosConfirmadosTotales = casosConfirmadosTotales;
    }

    public int getCasosConfirmadosUltimos14dias() {
        return casosConfirmadosUltimos14dias;
    }

    public void setCasosConfirmadosUltimos14dias(int casosConfirmadosUltimos14dias) {
        this.casosConfirmadosUltimos14dias = casosConfirmadosUltimos14dias;
    }

    public LocalDateTime getFechaInforme() {
        return fechaInforme;
    }

    public void setFechaInforme(LocalDateTime fechaInforme) {
        this.fechaInforme = fechaInforme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComunidadDailyData that = (ComunidadDailyData) o;

        if (Float.compare(that.tasaIncidenciaAcumuladaUltimos14dias, tasaIncidenciaAcumuladaUltimos14dias) != 0)
            return false;
        if (Float.compare(that.tasaIncidenciaAcumuladaTotal, tasaIncidenciaAcumuladaTotal) != 0) return false;
        if (casosConfirmadosTotales != that.casosConfirmadosTotales) return false;
        if (casosConfirmadosUltimos14dias != that.casosConfirmadosUltimos14dias) return false;
        if (codigoGeometria != null ? !codigoGeometria.equals(that.codigoGeometria) : that.codigoGeometria != null)
            return false;
        if (municipoDistrito != null ? !municipoDistrito.equals(that.municipoDistrito) : that.municipoDistrito != null)
            return false;
        return fechaInforme != null ? fechaInforme.equals(that.fechaInforme) : that.fechaInforme == null;
    }

    @Override
    public int hashCode() {
        int result = codigoGeometria != null ? codigoGeometria.hashCode() : 0;
        result = 31 * result + (municipoDistrito != null ? municipoDistrito.hashCode() : 0);
        result = 31 * result + (tasaIncidenciaAcumuladaUltimos14dias != +0.0f ? Float.floatToIntBits(tasaIncidenciaAcumuladaUltimos14dias) : 0);
        result = 31 * result + (tasaIncidenciaAcumuladaTotal != +0.0f ? Float.floatToIntBits(tasaIncidenciaAcumuladaTotal) : 0);
        result = 31 * result + casosConfirmadosTotales;
        result = 31 * result + casosConfirmadosUltimos14dias;
        result = 31 * result + (fechaInforme != null ? fechaInforme.hashCode() : 0);
        return result;
    }


}
