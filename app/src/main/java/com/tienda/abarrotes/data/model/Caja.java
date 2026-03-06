package com.tienda.abarrotes.data.model;

public class Caja {

    private int id;
    private int usuarioId;
    private String fechaApertura;
    private String fechaCierre;
    private double montoInicial;
    private double montoFinal;
    private int estado;

    public Caja() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public double getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public double getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(double montoFinal) {
        this.montoFinal = montoFinal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}