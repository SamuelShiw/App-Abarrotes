package com.tienda.abarrotes.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.TrabajadorDao;
import com.tienda.abarrotes.data.model.Trabajador;

import java.util.List;

public class TrabajadorRepository {

    private final TrabajadorDao trabajadorDao;

    public TrabajadorRepository(Context context) {
        this.trabajadorDao = new TrabajadorDao(context);
    }

    public long insertarTrabajador(Trabajador trabajador) {
        return trabajadorDao.insertarTrabajador(trabajador);
    }

    public boolean existeDni(String dni) {
        return trabajadorDao.existeDni(dni);
    }

    public Trabajador obtenerTrabajadorPorId(int trabajadorId) {
        return trabajadorDao.obtenerTrabajadorPorId(trabajadorId);
    }

    public List<Trabajador> listarTrabajadoresActivos() {
        return trabajadorDao.listarTrabajadoresActivos();
    }
}