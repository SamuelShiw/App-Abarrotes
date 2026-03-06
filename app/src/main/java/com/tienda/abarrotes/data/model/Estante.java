package com.tienda.abarrotes.data.model;

public class Estante {

    private int id;
    private int seccionId;
    private String codigo;
    private String descripcion;
    private int estado;

    public Estante() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(int seccionId) {
        this.seccionId = seccionId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}