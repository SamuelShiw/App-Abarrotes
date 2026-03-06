package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.Caja;

public class CajaDao {

    private final DatabaseHelper databaseHelper;

    public CajaDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public boolean existeCajaAbierta(int usuarioId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String sql = "SELECT " + DatabaseContract.CajaTable.COLUMN_ID +
                " FROM " + DatabaseContract.CajaTable.TABLE_NAME +
                " WHERE " + DatabaseContract.CajaTable.COLUMN_USUARIO_ID + " = ?" +
                " AND " + DatabaseContract.CajaTable.COLUMN_ESTADO + " = ? LIMIT 1";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(usuarioId), "ABIERTA"});
        boolean existe = cursor.moveToFirst();
        cursor.close();

        return existe;
    }

    public int obtenerCajaAbiertaId(int usuarioId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " + DatabaseContract.CajaTable.COLUMN_ID +
                    " FROM " + DatabaseContract.CajaTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.CajaTable.COLUMN_USUARIO_ID + " = ?" +
                    " AND " + DatabaseContract.CajaTable.COLUMN_ESTADO + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{String.valueOf(usuarioId), "ABIERTA"});

            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COLUMN_ID));
            }

            return -1;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public long abrirCaja(Caja caja) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CajaTable.COLUMN_USUARIO_ID, caja.getUsuarioId());
        values.put(DatabaseContract.CajaTable.COLUMN_FECHA_APERTURA, caja.getFechaApertura());
        values.put(DatabaseContract.CajaTable.COLUMN_MONTO_INICIAL, caja.getMontoInicial());
        values.put(DatabaseContract.CajaTable.COLUMN_ESTADO, "ABIERTA");

        return db.insert(DatabaseContract.CajaTable.TABLE_NAME, null, values);
    }

    public boolean cerrarCaja(int usuarioId, String fechaCierre, double montoFinal) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CajaTable.COLUMN_FECHA_CIERRE, fechaCierre);
        values.put(DatabaseContract.CajaTable.COLUMN_MONTO_FINAL, montoFinal);
        values.put(DatabaseContract.CajaTable.COLUMN_ESTADO, "CERRADA");

        int filas = db.update(
                DatabaseContract.CajaTable.TABLE_NAME,
                values,
                DatabaseContract.CajaTable.COLUMN_USUARIO_ID + " = ? AND " +
                        DatabaseContract.CajaTable.COLUMN_ESTADO + " = ?",
                new String[]{String.valueOf(usuarioId), "ABIERTA"}
        );

        return filas > 0;
    }

    public Caja obtenerCajaAbierta(int usuarioId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Caja caja = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.CajaTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.CajaTable.COLUMN_USUARIO_ID + " = ?" +
                    " AND " + DatabaseContract.CajaTable.COLUMN_ESTADO + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{String.valueOf(usuarioId), "ABIERTA"});

            if (cursor.moveToFirst()) {
                caja = new Caja();
                caja.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COLUMN_ID)));
                caja.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COLUMN_USUARIO_ID)));
                caja.setFechaApertura(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COLUMN_FECHA_APERTURA)));
                caja.setMontoInicial(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.CajaTable.COLUMN_MONTO_INICIAL)));
            }

            return caja;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}