package com.tienda.abarrotes.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.Rol;

import java.util.ArrayList;
import java.util.List;

public class RolDao {

    private final DatabaseHelper databaseHelper;

    public RolDao(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public Rol obtenerRolPorNombre(String nombreRol) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Rol rol = null;

        try {
            String sql = "SELECT " +
                    DatabaseContract.RolTable.COLUMN_ID + ", " +
                    DatabaseContract.RolTable.COLUMN_NOMBRE +
                    " FROM " + DatabaseContract.RolTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.RolTable.COLUMN_NOMBRE + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{nombreRol});

            if (cursor.moveToFirst()) {
                rol = new Rol();
                rol.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RolTable.COLUMN_ID)));
                rol.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.RolTable.COLUMN_NOMBRE)));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return rol;
    }

    public int obtenerIdRolPorNombre(String nombreRol) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " +
                    DatabaseContract.RolTable.COLUMN_ID +
                    " FROM " + DatabaseContract.RolTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.RolTable.COLUMN_NOMBRE + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{nombreRol});

            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RolTable.COLUMN_ID));
            }

            return -1;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public List<Rol> listarRoles() {
        List<Rol> lista = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " +
                    DatabaseContract.RolTable.COLUMN_ID + ", " +
                    DatabaseContract.RolTable.COLUMN_NOMBRE +
                    " FROM " + DatabaseContract.RolTable.TABLE_NAME +
                    " ORDER BY " + DatabaseContract.RolTable.COLUMN_NOMBRE + " ASC";

            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                Rol rol = new Rol();
                rol.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.RolTable.COLUMN_ID)));
                rol.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.RolTable.COLUMN_NOMBRE)));
                lista.add(rol);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lista;
    }
}