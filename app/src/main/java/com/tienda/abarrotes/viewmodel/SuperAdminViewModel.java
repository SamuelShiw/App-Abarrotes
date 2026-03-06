package com.tienda.abarrotes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.UsuarioAdminListado;
import com.tienda.abarrotes.repository.UsuarioRepository;

import java.util.List;

public class SuperAdminViewModel extends AndroidViewModel {

    private final UsuarioRepository usuarioRepository;

    public SuperAdminViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
    }

    public List<UsuarioAdminListado> listarAdministradores() {
        return usuarioRepository.listarAdministradores();
    }
}