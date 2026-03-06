package com.tienda.abarrotes.data.model;

public class Usuario {

    private int id;
    private int trabajadorId;
    private int rolId;
    private String username;
    private String password;
    private int estado;

    public Usuario() {
    }

    public Usuario(int id, int trabajadorId, int rolId, String username, String password, int estado) {
        this.id = id;
        this.trabajadorId = trabajadorId;
        this.rolId = rolId;
        this.username = username;
        this.password = password;
        this.estado = estado;
    }

    public Usuario(int trabajadorId, int rolId, String username, String password, int estado) {
        this.trabajadorId = trabajadorId;
        this.rolId = rolId;
        this.username = username;
        this.password = password;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(int trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean isActivo() {
        return estado == 1;
    }
}