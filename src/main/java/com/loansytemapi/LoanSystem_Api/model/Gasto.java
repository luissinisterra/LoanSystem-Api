package com.loansytemapi.LoanSystem_Api.model;

import java.time.LocalDateTime;
import java.util.UUID;
public class Gasto {

    private String idGasto;
    private String tipoDeGasto;
    private String descripcionGasto;
    private double valorGasto;
    private LocalDateTime fechaGasto;

    public Gasto() {
        idGasto = UUID.randomUUID().toString();
        fechaGasto = LocalDateTime.now();
    }
    public Gasto (String tipoDeGasto, String descripcionGasto,  double valorGasto) {
        idGasto = UUID.randomUUID().toString();
        fechaGasto = LocalDateTime.now();
        this.tipoDeGasto = tipoDeGasto;
        this.descripcionGasto = descripcionGasto;
        this.valorGasto = valorGasto;
    }

    public double getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(double valorGasto) {
        this.valorGasto = valorGasto;
    }

    public String getDescripcionGasto() {
        return descripcionGasto;
    }

    public void setDescripcionGasto(String descripcionGasto) {
        this.descripcionGasto = descripcionGasto;
    }

    public String getTipoDeGasto() {
        return tipoDeGasto;
    }

    public void setTipoDeGasto(String tipoDeGasto) {
        this.tipoDeGasto = tipoDeGasto;
    }

    public String getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(String idGasto) {
        this.idGasto = idGasto;
    }
    public LocalDateTime getFechaGasto() {
        return fechaGasto;
    }
    public void setFechaGasto(LocalDateTime fechaGasto) {
        this.fechaGasto = fechaGasto;
    }
}
