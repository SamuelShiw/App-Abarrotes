package com.tienda.abarrotes.data.model;

public class UsuarioAdminListado {

    private int usuarioId;
    private int trabajadorId;
    private String nombres;
    private String apellidos;
    private String dni;
    private String username;
    private String rol;

    public UsuarioAdminListado() {
    }

    public UsuarioAdminListado(int usuarioId, int trabajadorId, String nombres, String apellidos,
                               String dni, String username, String rol) {
        this.usuarioId = usuarioId;
        this.trabajadorId = trabajadorId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.username = username;
        this.rol = rol;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(int trabajadorId) {
        this.trabajadorId = trabajadorId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}