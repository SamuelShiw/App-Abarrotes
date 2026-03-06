package com.tienda.abarrotes.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.UsuarioDao;
import com.tienda.abarrotes.data.model.Usuario;
import com.tienda.abarrotes.data.model.UsuarioAdminListado;

import java.util.List;

public class UsuarioRepository {

    private final UsuarioDao usuarioDao;

    public UsuarioRepository(Context context) {
        this.usuarioDao = new UsuarioDao(context);
    }

    public long insertarUsuario(Usuario usuario) {
        return usuarioDao.insertarUsuario(usuario);
    }

    public boolean existeUsername(String username) {
        return usuarioDao.existeUsername(username);
    }

    public boolean existeUsuarioActivoPorTrabajador(int trabajadorId) {
        return usuarioDao.existeUsuarioActivoPorTrabajador(trabajadorId);
    }

    public List<UsuarioAdminListado> listarAdministradores() {
        return usuarioDao.listarAdministradores();
    }
}