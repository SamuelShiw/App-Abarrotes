package com.tienda.abarrotes.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.CajaDao;
import com.tienda.abarrotes.data.model.Caja;

public class CajaRepository {

    private final CajaDao cajaDao;

    public CajaRepository(Context context) {
        cajaDao = new CajaDao(context);
    }

    public boolean existeCajaAbierta(int usuarioId) {
        return cajaDao.existeCajaAbierta(usuarioId);
    }

    public long abrirCaja(Caja caja) {
        return cajaDao.abrirCaja(caja);
    }

    public boolean cerrarCaja(int usuarioId, String fechaCierre, double montoFinal) {
        return cajaDao.cerrarCaja(usuarioId, fechaCierre, montoFinal);
    }

    public Caja obtenerCajaAbierta(int usuarioId) {
        return cajaDao.obtenerCajaAbierta(usuarioId);
    }
}