package com.tienda.abarrotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.Trabajador;
import com.tienda.abarrotes.repository.TrabajadorRepository;

import java.util.List;

public class TrabajadorViewModel extends AndroidViewModel {

    private final TrabajadorRepository trabajadorRepository;

    public TrabajadorViewModel(@NonNull Application application) {
        super(application);
        trabajadorRepository = new TrabajadorRepository(application);
    }

    public List<Trabajador> listarTrabajadoresActivos() {
        return trabajadorRepository.listarTrabajadoresActivos();
    }

    public boolean existeDni(String dni) {
        return trabajadorRepository.existeDni(dni);
    }

    public long insertarTrabajador(Trabajador trabajador) {
        return trabajadorRepository.insertarTrabajador(trabajador);
    }

    public String validarCampos(String nombres, String apellidos, String dni, String cargo) {
        if (TextUtils.isEmpty(nombres)) {
            return "Ingrese los nombres";
        }

        if (TextUtils.isEmpty(apellidos)) {
            return "Ingrese los apellidos";
        }

        if (TextUtils.isEmpty(dni)) {
            return "Ingrese el DNI";
        }

        if (dni.length() != 8) {
            return "El DNI debe tener 8 dígitos";
        }

        if (!TextUtils.isDigitsOnly(dni)) {
            return "El DNI solo debe contener números";
        }

        if (TextUtils.isEmpty(cargo)) {
            return "Ingrese el cargo";
        }

        return null;
    }
}