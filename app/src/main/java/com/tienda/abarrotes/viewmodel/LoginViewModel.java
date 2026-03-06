package com.tienda.abarrotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.Trabajador;
import com.tienda.abarrotes.data.model.Usuario;
import com.tienda.abarrotes.repository.AuthRepository;

public class LoginViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public String validarCampos(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            return "Ingrese el usuario";
        }

        if (TextUtils.isEmpty(password)) {
            return "Ingrese la contraseña";
        }

        return null;
    }

    public Usuario login(String username, String password) {
        return authRepository.login(username, password);
    }

    public Trabajador obtenerTrabajadorPorId(int trabajadorId) {
        return authRepository.obtenerTrabajadorPorId(trabajadorId);
    }

    public String obtenerNombreRolPorId(int rolId) {
        return authRepository.obtenerNombreRolPorId(rolId);
    }
}