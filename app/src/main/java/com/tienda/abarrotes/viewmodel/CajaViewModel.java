package com.tienda.abarrotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.Caja;
import com.tienda.abarrotes.repository.CajaRepository;

public class CajaViewModel extends AndroidViewModel {

    private final CajaRepository cajaRepository;

    public CajaViewModel(@NonNull Application application) {
        super(application);
        cajaRepository = new CajaRepository(application);
    }

    public boolean existeCajaAbierta(int usuarioId) {
        return cajaRepository.existeCajaAbierta(usuarioId);
    }

    public long abrirCaja(Caja caja) {
        return cajaRepository.abrirCaja(caja);
    }

    public boolean cerrarCaja(int usuarioId, String fechaCierre, double montoFinal) {
        return cajaRepository.cerrarCaja(usuarioId, fechaCierre, montoFinal);
    }

    public Caja obtenerCajaAbierta(int usuarioId) {
        return cajaRepository.obtenerCajaAbierta(usuarioId);
    }

    public String validarMonto(String montoTexto) {
        if (TextUtils.isEmpty(montoTexto)) {
            return "Ingrese el monto";
        }

        try {
            double monto = Double.parseDouble(montoTexto);
            if (monto < 0) {
                return "El monto no puede ser negativo";
            }
        } catch (Exception e) {
            return "Monto inválido";
        }

        return null;
    }
}