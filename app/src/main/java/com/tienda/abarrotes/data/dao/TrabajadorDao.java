package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.Trabajador;

import java.util.ArrayList;
import java.util.List;

public class TrabajadorDao {

    private final DatabaseHelper databaseHelper;

    public TrabajadorDao(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long insertarTrabajador(Trabajador trabajador) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.TrabajadorTable.COLUMN_NOMBRES, trabajador.getNombres());
        values.put(DatabaseContract.TrabajadorTable.COLUMN_APELLIDOS, trabajador.getApellidos());
        values.put(DatabaseContract.TrabajadorTable.COLUMN_DNI, trabajador.getDni());
        values.put(DatabaseContract.TrabajadorTable.COLUMN_TELEFONO, trabajador.getTelefono());
        values.put(DatabaseContract.TrabajadorTable.COLUMN_CARGO, trabajador.getCargo());
        values.put(DatabaseContract.TrabajadorTable.COLUMN_ESTADO, trabajador.getEstado());

        return db.insert(DatabaseContract.TrabajadorTable.TABLE_NAME, null, values);
    }

    public boolean existeDni(String dni) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " +
                    DatabaseContract.TrabajadorTable.COLUMN_ID +
                    " FROM " + DatabaseContract.TrabajadorTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.TrabajadorTable.COLUMN_DNI + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{dni});
            return cursor.moveToFirst();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Trabajador obtenerTrabajadorPorId(int trabajadorId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Trabajador trabajador = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.TrabajadorTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.TrabajadorTable.COLUMN_ID + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{String.valueOf(trabajadorId)});

            if (cursor.moveToFirst()) {
                trabajador = new Trabajador();
                trabajador.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_ID)));
                trabajador.setNombres(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_NOMBRES)));
                trabajador.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_APELLIDOS)));
                trabajador.setDni(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_DNI)));
                trabajador.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_TELEFONO)));
                trabajador.setCargo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_CARGO)));
                trabajador.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_ESTADO)));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return trabajador;
    }

    public List<Trabajador> listarTrabajadoresActivos() {
        List<Trabajador> lista = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.TrabajadorTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.TrabajadorTable.COLUMN_ESTADO + " = 1" +
                    " ORDER BY " + DatabaseContract.TrabajadorTable.COLUMN_NOMBRES + " ASC, " +
                    DatabaseContract.TrabajadorTable.COLUMN_APELLIDOS + " ASC";

            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                Trabajador trabajador = new Trabajador();
                trabajador.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_ID)));
                trabajador.setNombres(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_NOMBRES)));
                trabajador.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_APELLIDOS)));
                trabajador.setDni(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_DNI)));
                trabajador.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_TELEFONO)));
                trabajador.setCargo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_CARGO)));
                trabajador.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_ESTADO)));
                lista.add(trabajador);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lista;
    }
}