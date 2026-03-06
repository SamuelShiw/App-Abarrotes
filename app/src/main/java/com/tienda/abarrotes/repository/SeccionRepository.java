package com.tienda.abarrotes.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.SeccionDao;
import com.tienda.abarrotes.data.model.Seccion;

import java.util.List;

public class SeccionRepository {

    private final SeccionDao seccionDao;

    public SeccionRepository(Context context) {
        this.seccionDao = new SeccionDao(context);
    }

    public long insertarSeccion(Seccion seccion) {
        return seccionDao.insertarSeccion(seccion);
    }

    public boolean existeNombre(String nombre) {
        return seccionDao.existeNombre(nombre);
    }

    public List<Seccion> listarSeccionesActivas() {
        return seccionDao.listarSeccionesActivas();
    }
}