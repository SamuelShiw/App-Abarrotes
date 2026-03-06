package com.tienda.abarrotes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.Trabajador;
import com.tienda.abarrotes.repository.TrabajadorRepository;
import com.tienda.abarrotes.ui.common.utils.ValidationUtils;

import java.util.ArrayList;
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

    public String validarCampos(String nombres,
                                String apellidos,
                                String dni,
                                String telefono,
                                String cargo,
                                String rolLogueado) {

        if (ValidationUtils.isNullOrEmpty(nombres)) {
            return "Ingrese los nombres";
        }

        if (ValidationUtils.isNullOrEmpty(apellidos)) {
            return "Ingrese los apellidos";
        }

        if (ValidationUtils.isNullOrEmpty(dni)) {
            return "Ingrese el DNI";
        }

        if (!ValidationUtils.isValidDni(dni)) {
            return "El DNI debe tener 8 dígitos numéricos";
        }

        if (ValidationUtils.isNullOrEmpty(telefono)) {
            return "Ingrese el teléfono";
        }

        if (!ValidationUtils.isValidPhone(telefono)) {
            return "El teléfono debe tener 9 dígitos numéricos";
        }

        if (ValidationUtils.isNullOrEmpty(cargo)) {
            return "Seleccione un cargo";
        }

        if (!cargoPermitidoParaRol(cargo, rolLogueado)) {
            return "No tiene permisos para registrar ese cargo";
        }

        return null;
    }

    private boolean cargoPermitidoParaRol(String cargo, String rolLogueado) {
        List<String> cargosPermitidos = obtenerCargosPermitidos(rolLogueado);
        return cargosPermitidos.contains(cargo);
    }

    public List<String> obtenerCargosPermitidos(String rolLogueado) {
        List<String> lista = new ArrayList<>();

        if ("SUPERADMINISTRADOR".equalsIgnoreCase(rolLogueado)) {
            lista.add("ADMINISTRADOR");
            lista.add("CAJERO");
            lista.add("REPONEDOR");
        } else if ("ADMINISTRADOR".equalsIgnoreCase(rolLogueado)) {
            lista.add("CAJERO");
            lista.add("REPONEDOR");
        }

        return lista;
    }
}