package com.tienda.abarrotes.data.model;

public class Trabajador {

    private int id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String cargo;
    private int estado;

    public Trabajador() {
    }

    public Trabajador(int id, String nombres, String apellidos, String dni,
                      String telefono, String cargo, int estado) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.cargo = cargo;
        this.estado = estado;
    }

    public Trabajador(String nombres, String apellidos, String dni,
                      String telefono, String cargo, int estado) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.cargo = cargo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}