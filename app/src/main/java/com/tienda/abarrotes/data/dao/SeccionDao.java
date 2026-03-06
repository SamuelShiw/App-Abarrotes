package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.Seccion;

import java.util.ArrayList;
import java.util.List;

public class SeccionDao {

    private final DatabaseHelper databaseHelper;

    public SeccionDao(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long insertarSeccion(Seccion seccion) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.SeccionTable.COLUMN_NOMBRE, seccion.getNombre());
        values.put(DatabaseContract.SeccionTable.COLUMN_DESCRIPCION, seccion.getDescripcion());
        values.put(DatabaseContract.SeccionTable.COLUMN_ESTADO, seccion.getEstado());

        return db.insert(DatabaseContract.SeccionTable.TABLE_NAME, null, values);
    }

    public boolean existeNombre(String nombre) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " + DatabaseContract.SeccionTable.COLUMN_ID +
                    " FROM " + DatabaseContract.SeccionTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.SeccionTable.COLUMN_NOMBRE + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{nombre});
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Seccion obtenerSeccionPorId(int seccionId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Seccion seccion = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.SeccionTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.SeccionTable.COLUMN_ID + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{String.valueOf(seccionId)});

            if (cursor.moveToFirst()) {
                seccion = new Seccion();
                seccion.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.SeccionTable.COLUMN_ID)));
                seccion.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.SeccionTable.COLUMN_NOMBRE)));
                seccion.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.SeccionTable.COLUMN_DESCRIPCION)));
                seccion.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.SeccionTable.COLUMN_ESTADO)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return seccion;
    }

    public List<Seccion> listarSeccionesActivas() {
        List<Seccion> lista = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.SeccionTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.SeccionTable.COLUMN_ESTADO + " = 1" +
                    " ORDER BY " + DatabaseContract.SeccionTable.COLUMN_NOMBRE + " ASC";

            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                Seccion seccion = new Seccion();
                seccion.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.SeccionTable.COLUMN_ID)));
                seccion.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.SeccionTable.COLUMN_NOMBRE)));
                seccion.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.SeccionTable.COLUMN_DESCRIPCION)));
                seccion.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.SeccionTable.COLUMN_ESTADO)));
                lista.add(seccion);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lista;
    }
}