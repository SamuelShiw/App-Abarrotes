package com.tienda.abarrotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.dao.RolDao;
import com.tienda.abarrotes.data.model.Rol;
import com.tienda.abarrotes.data.model.Usuario;
import com.tienda.abarrotes.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {

    private final UsuarioRepository usuarioRepository;
    private final RolDao rolDao;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
        rolDao = new RolDao(application);
    }

    public String validarCampos(int trabajadorId,
                                String username,
                                String password,
                                String nombreRol,
                                String rolLogueado) {

        if (trabajadorId <= 0) {
            return "Seleccione un trabajador";
        }

        if (TextUtils.isEmpty(username)) {
            return "Ingrese el username";
        }

        if (username.length() < 4) {
            return "El username debe tener al menos 4 caracteres";
        }

        if (TextUtils.isEmpty(password)) {
            return "Ingrese la contraseña";
        }

        if (password.length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres";
        }

        if (TextUtils.isEmpty(nombreRol)) {
            return "Seleccione un rol";
        }

        if (!rolPermitidoParaSesion(nombreRol, rolLogueado)) {
            return "No tiene permisos para asignar ese rol";
        }

        return null;
    }

    public boolean existeUsername(String username) {
        return usuarioRepository.existeUsername(username);
    }

    public boolean existeUsuarioActivoPorTrabajador(int trabajadorId) {
        return usuarioRepository.existeUsuarioActivoPorTrabajador(trabajadorId);
    }

    public Rol obtenerRolPorNombre(String nombreRol) {
        return rolDao.obtenerRolPorNombre(nombreRol);
    }

    public long insertarUsuario(Usuario usuario) {
        return usuarioRepository.insertarUsuario(usuario);
    }

    public List<String> obtenerRolesDisponiblesSegunSesion(String rolLogueado) {
        List<String> roles = new ArrayList<>();
        roles.add("Seleccione un rol");

        if ("SUPERADMINISTRADOR".equalsIgnoreCase(rolLogueado)) {
            roles.add("ADMINISTRADOR");
            roles.add("CAJERO");
            roles.add("REPONEDOR");
        } else if ("ADMINISTRADOR".equalsIgnoreCase(rolLogueado)) {
            roles.add("CAJERO");
            roles.add("REPONEDOR");
        }

        return roles;
    }

    private boolean rolPermitidoParaSesion(String nombreRol, String rolLogueado) {
        List<String> rolesPermitidos = obtenerRolesDisponiblesSegunSesion(rolLogueado);
        return rolesPermitidos.contains(nombreRol);
    }
}