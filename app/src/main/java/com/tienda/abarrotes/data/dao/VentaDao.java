package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.Venta;

public class VentaDao {

    private final DatabaseHelper databaseHelper;

    public VentaDao(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long insertarVenta(Venta venta) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.VentaTable.COLUMN_CAJA_ID, venta.getCajaId());
        values.put(DatabaseContract.VentaTable.COLUMN_USUARIO_ID, venta.getUsuarioId());
        values.put(DatabaseContract.VentaTable.COLUMN_FECHA, venta.getFecha());
        values.put(DatabaseContract.VentaTable.COLUMN_TOTAL, venta.getTotal());
        values.put(DatabaseContract.VentaTable.COLUMN_TIPO_COMPROBANTE, venta.getTipoComprobante());

        return db.insert(DatabaseContract.VentaTable.TABLE_NAME, null, values);
    }
}