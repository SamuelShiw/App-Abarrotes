package com.tienda.abarrotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.Seccion;
import com.tienda.abarrotes.repository.SeccionRepository;

import java.util.List;

public class SeccionViewModel extends AndroidViewModel {

    private final SeccionRepository seccionRepository;

    public SeccionViewModel(@NonNull Application application) {
        super(application);
        seccionRepository = new SeccionRepository(application);
    }

    public List<Seccion> listarSeccionesActivas() {
        return seccionRepository.listarSeccionesActivas();
    }

    public boolean existeNombre(String nombre) {
        return seccionRepository.existeNombre(nombre);
    }

    public long insertarSeccion(Seccion seccion) {
        return seccionRepository.insertarSeccion(seccion);
    }

    public String validarCampos(String nombre) {
        if (TextUtils.isEmpty(nombre)) {
            return "Ingrese el nombre de la sección";
        }
        return null;
    }
}