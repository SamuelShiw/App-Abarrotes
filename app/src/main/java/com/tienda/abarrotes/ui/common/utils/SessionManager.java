package com.tienda.abarrotes.ui.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "abarrotes_session";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USUARIO_ID = "usuario_id";
    private static final String KEY_TRABAJADOR_ID = "trabajador_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ROL_ID = "rol_id";
    private static final String KEY_ROL_NOMBRE = "rol_nombre";
    private static final String KEY_NOMBRE_COMPLETO = "nombre_completo";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void guardarSesion(int usuarioId, int trabajadorId, String username,
                              int rolId, String rolNombre, String nombreCompleto) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USUARIO_ID, usuarioId);
        editor.putInt(KEY_TRABAJADOR_ID, trabajadorId);
        editor.putString(KEY_USERNAME, username);
        editor.putInt(KEY_ROL_ID, rolId);
        editor.putString(KEY_ROL_NOMBRE, rolNombre);
        editor.putString(KEY_NOMBRE_COMPLETO, nombreCompleto);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public int getUsuarioId() {
        return sharedPreferences.getInt(KEY_USUARIO_ID, -1);
    }

    public int getTrabajadorId() {
        return sharedPreferences.getInt(KEY_TRABAJADOR_ID, -1);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public int getRolId() {
        return sharedPreferences.getInt(KEY_ROL_ID, -1);
    }

    public String getRolNombre() {
        return sharedPreferences.getString(KEY_ROL_NOMBRE, "");
    }

    public String getNombreCompleto() {
        return sharedPreferences.getString(KEY_NOMBRE_COMPLETO, "");
    }

    public void cerrarSesion() {
        editor.clear();
        editor.apply();
    }
}