package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.Usuario;
import com.tienda.abarrotes.data.model.UsuarioAdminListado;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private final DatabaseHelper databaseHelper;

    public UsuarioDao(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.UsuarioTable.COLUMN_TRABAJADOR_ID, usuario.getTrabajadorId());
        values.put(DatabaseContract.UsuarioTable.COLUMN_ROL_ID, usuario.getRolId());
        values.put(DatabaseContract.UsuarioTable.COLUMN_USERNAME, usuario.getUsername());
        values.put(DatabaseContract.UsuarioTable.COLUMN_PASSWORD, usuario.getPassword());
        values.put(DatabaseContract.UsuarioTable.COLUMN_ESTADO, usuario.getEstado());

        return db.insert(DatabaseContract.UsuarioTable.TABLE_NAME, null, values);
    }

    public boolean existeUsername(String username) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " +
                    DatabaseContract.UsuarioTable.COLUMN_ID +
                    " FROM " + DatabaseContract.UsuarioTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.UsuarioTable.COLUMN_USERNAME + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{username});
            return cursor.moveToFirst();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public boolean existeUsuarioActivoPorTrabajador(int trabajadorId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " +
                    DatabaseContract.UsuarioTable.COLUMN_ID +
                    " FROM " + DatabaseContract.UsuarioTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.UsuarioTable.COLUMN_TRABAJADOR_ID + " = ?" +
                    " AND " + DatabaseContract.UsuarioTable.COLUMN_ESTADO + " = 1 LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{String.valueOf(trabajadorId)});
            return cursor.moveToFirst();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Usuario autenticarUsuario(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Usuario usuario = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.UsuarioTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.UsuarioTable.COLUMN_USERNAME + " = ?" +
                    " AND " + DatabaseContract.UsuarioTable.COLUMN_PASSWORD + " = ?" +
                    " AND " + DatabaseContract.UsuarioTable.COLUMN_ESTADO + " = 1" +
                    " LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{username, password});

            if (cursor.moveToFirst()) {
                usuario = new Usuario();
                usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuarioTable.COLUMN_ID)));
                usuario.setTrabajadorId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuarioTable.COLUMN_TRABAJADOR_ID)));
                usuario.setRolId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuarioTable.COLUMN_ROL_ID)));
                usuario.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UsuarioTable.COLUMN_USERNAME)));
                usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UsuarioTable.COLUMN_PASSWORD)));
                usuario.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UsuarioTable.COLUMN_ESTADO)));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return usuario;
    }

    public List<UsuarioAdminListado> listarAdministradores() {
        List<UsuarioAdminListado> lista = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " +
                    "u.id AS usuario_id, " +
                    "t.id AS trabajador_id, " +
                    "t.nombres AS nombres, " +
                    "t.apellidos AS apellidos, " +
                    "t.dni AS dni, " +
                    "u.username AS username, " +
                    "r.nombre AS rol " +
                    "FROM " + DatabaseContract.UsuarioTable.TABLE_NAME + " u " +
                    "INNER JOIN " + DatabaseContract.TrabajadorTable.TABLE_NAME + " t " +
                    "ON u." + DatabaseContract.UsuarioTable.COLUMN_TRABAJADOR_ID + " = t." + DatabaseContract.TrabajadorTable.COLUMN_ID + " " +
                    "INNER JOIN " + DatabaseContract.RolTable.TABLE_NAME + " r " +
                    "ON u." + DatabaseContract.UsuarioTable.COLUMN_ROL_ID + " = r." + DatabaseContract.RolTable.COLUMN_ID + " " +
                    "WHERE r." + DatabaseContract.RolTable.COLUMN_NOMBRE + " = ? " +
                    "ORDER BY t." + DatabaseContract.TrabajadorTable.COLUMN_NOMBRES + " ASC, " +
                    "t." + DatabaseContract.TrabajadorTable.COLUMN_APELLIDOS + " ASC";

            cursor = db.rawQuery(sql, new String[]{"ADMINISTRADOR"});

            while (cursor.moveToNext()) {
                UsuarioAdminListado item = new UsuarioAdminListado();
                item.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
                item.setTrabajadorId(cursor.getInt(cursor.getColumnIndexOrThrow("trabajador_id")));
                item.setNombres(cursor.getString(cursor.getColumnIndexOrThrow("nombres")));
                item.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow("apellidos")));
                item.setDni(cursor.getString(cursor.getColumnIndexOrThrow("dni")));
                item.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                item.setRol(cursor.getString(cursor.getColumnIndexOrThrow("rol")));
                lista.add(item);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lista;
    }
}