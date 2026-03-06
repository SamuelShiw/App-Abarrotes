package com.tienda.abarrotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.Estante;
import com.tienda.abarrotes.repository.EstanteRepository;
import com.tienda.abarrotes.repository.SeccionRepository;

import java.util.List;

public class EstanteViewModel extends AndroidViewModel {

    private final EstanteRepository estanteRepository;
    private final SeccionRepository seccionRepository;

    public EstanteViewModel(@NonNull Application application) {
        super(application);
        estanteRepository = new EstanteRepository(application);
        seccionRepository = new SeccionRepository(application);
    }

    public List<Estante> listarEstantesActivos() {
        return estanteRepository.listarEstantesActivos();
    }

    public boolean existeCodigo(String codigo) {
        return estanteRepository.existeCodigo(codigo);
    }

    public long insertarEstante(Estante estante) {
        return estanteRepository.insertarEstante(estante);
    }

    public String validarCampos(String seccionIdTexto, String codigo) {
        if (TextUtils.isEmpty(seccionIdTexto)) {
            return "Ingrese el ID de la sección";
        }

        try {
            int seccionId = Integer.parseInt(seccionIdTexto);
            if (seccionId <= 0) {
                return "ID de sección inválido";
            }
        } catch (Exception e) {
            return "ID de sección inválido";
        }

        if (TextUtils.isEmpty(codigo)) {
            return "Ingrese el código del estante";
        }

        return null;
    }

    public boolean existeSeccion(int seccionId) {
        return seccionRepository.listarSeccionesActivas()
                .stream()
                .anyMatch(seccion -> seccion.getId() == seccionId);
    }
}