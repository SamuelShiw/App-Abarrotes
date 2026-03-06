package com.tienda.abarrotes.data.model;

public class Producto {

    private int id;
    private int seccionId;
    private int estanteId;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stockAlmacen;
    private int stockEstante;
    private String fechaVencimiento;
    private int estado;

    public Producto() {
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

    public int getEstanteId() {
        return estanteId;
    }

    public void setEstanteId(int estanteId) {
        this.estanteId = estanteId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStockAlmacen() {
        return stockAlmacen;
    }

    public void setStockAlmacen(int stockAlmacen) {
        this.stockAlmacen = stockAlmacen;
    }

    public int getStockEstante() {
        return stockEstante;
    }

    public void setStockEstante(int stockEstante) {
        this.stockEstante = stockEstante;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}