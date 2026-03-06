package com.tienda.abarrotes.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.EstanteDao;
import com.tienda.abarrotes.data.model.Estante;

import java.util.List;

public class EstanteRepository {

    private final EstanteDao estanteDao;

    public EstanteRepository(Context context) {
        this.estanteDao = new EstanteDao(context);
    }

    public long insertarEstante(Estante estante) {
        return estanteDao.insertarEstante(estante);
    }

    public boolean existeCodigo(String codigo) {
        return estanteDao.existeCodigo(codigo);
    }

    public List<Estante> listarEstantesActivos() {
        return estanteDao.listarEstantesActivos();
    }
}