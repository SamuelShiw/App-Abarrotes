package com.tienda.abarrotes.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.RolDao;
import com.tienda.abarrotes.data.dao.TrabajadorDao;
import com.tienda.abarrotes.data.dao.UsuarioDao;
import com.tienda.abarrotes.data.model.Rol;
import com.tienda.abarrotes.data.model.Trabajador;
import com.tienda.abarrotes.data.model.Usuario;

public class AuthRepository {

    private final UsuarioDao usuarioDao;
    private final TrabajadorDao trabajadorDao;
    private final RolDao rolDao;

    public AuthRepository(Context context) {
        usuarioDao = new UsuarioDao(context);
        trabajadorDao = new TrabajadorDao(context);
        rolDao = new RolDao(context);
    }

    public Usuario login(String username, String password) {
        return usuarioDao.autenticarUsuario(username, password);
    }

    public Trabajador obtenerTrabajadorPorId(int trabajadorId) {
        return trabajadorDao.obtenerTrabajadorPorId(trabajadorId);
    }

    public Rol obtenerRolPorIdNombre(String nombreRol) {
        return rolDao.obtenerRolPorNombre(nombreRol);
    }

    public String obtenerNombreRolPorId(int rolId) {
        if (rolDao.obtenerIdRolPorNombre("SUPERADMINISTRADOR") == rolId) {
            return "SUPERADMINISTRADOR";
        }
        if (rolDao.obtenerIdRolPorNombre("ADMINISTRADOR") == rolId) {
            return "ADMINISTRADOR";
        }
        if (rolDao.obtenerIdRolPorNombre("CAJERO") == rolId) {
            return "CAJERO";
        }
        if (rolDao.obtenerIdRolPorNombre("REPONEDOR") == rolId) {
            return "REPONEDOR";
        }
        return "";
    }
}