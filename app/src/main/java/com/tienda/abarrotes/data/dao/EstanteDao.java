package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.Estante;

import java.util.ArrayList;
import java.util.List;

public class EstanteDao {

    private final DatabaseHelper databaseHelper;

    public EstanteDao(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long insertarEstante(Estante estante) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.EstanteTable.COLUMN_SECCION_ID, estante.getSeccionId());
        values.put(DatabaseContract.EstanteTable.COLUMN_CODIGO, estante.getCodigo());
        values.put(DatabaseContract.EstanteTable.COLUMN_DESCRIPCION, estante.getDescripcion());
        values.put(DatabaseContract.EstanteTable.COLUMN_ESTADO, estante.getEstado());

        return db.insert(DatabaseContract.EstanteTable.TABLE_NAME, null, values);
    }

    public boolean existeCodigo(String codigo) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " + DatabaseContract.EstanteTable.COLUMN_ID +
                    " FROM " + DatabaseContract.EstanteTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.EstanteTable.COLUMN_CODIGO + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{codigo});
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Estante obtenerEstantePorId(int estanteId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Estante estante = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.EstanteTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.EstanteTable.COLUMN_ID + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{String.valueOf(estanteId)});

            if (cursor.moveToFirst()) {
                estante = new Estante();
                estante.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_ID)));
                estante.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_SECCION_ID)));
                estante.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_CODIGO)));
                estante.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_DESCRIPCION)));
                estante.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_ESTADO)));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return estante;
    }

    public List<Estante> listarEstantesActivos() {
        List<Estante> lista = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.EstanteTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.EstanteTable.COLUMN_ESTADO + " = 1" +
                    " ORDER BY " + DatabaseContract.EstanteTable.COLUMN_CODIGO + " ASC";

            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                Estante estante = new Estante();
                estante.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_ID)));
                estante.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_SECCION_ID)));
                estante.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_CODIGO)));
                estante.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_DESCRIPCION)));
                estante.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_ESTADO)));
                lista.add(estante);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lista;
    }

    public List<Estante> listarEstantesActivosPorSeccion(int seccionId) {
        List<Estante> lista = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.EstanteTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.EstanteTable.COLUMN_ESTADO + " = 1" +
                    " AND " + DatabaseContract.EstanteTable.COLUMN_SECCION_ID + " = ?" +
                    " ORDER BY " + DatabaseContract.EstanteTable.COLUMN_CODIGO + " ASC";

            cursor = db.rawQuery(sql, new String[]{String.valueOf(seccionId)});

            while (cursor.moveToNext()) {
                Estante estante = new Estante();
                estante.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_ID)));
                estante.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_SECCION_ID)));
                estante.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_CODIGO)));
                estante.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_DESCRIPCION)));
                estante.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstanteTable.COLUMN_ESTADO)));
                lista.add(estante);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lista;
    }
}