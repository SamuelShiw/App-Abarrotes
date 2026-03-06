package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.DetalleVenta;

public class DetalleVentaDao {

    private final DatabaseHelper databaseHelper;

    public DetalleVentaDao(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long insertarDetalleVenta(DetalleVenta detalleVenta) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DetalleVentaTable.COLUMN_VENTA_ID, detalleVenta.getVentaId());
        values.put(DatabaseContract.DetalleVentaTable.COLUMN_PRODUCTO_ID, detalleVenta.getProductoId());
        values.put(DatabaseContract.DetalleVentaTable.COLUMN_CANTIDAD, detalleVenta.getCantidad());
        values.put(DatabaseContract.DetalleVentaTable.COLUMN_PRECIO_UNITARIO, detalleVenta.getPrecioUnitario());
        values.put(DatabaseContract.DetalleVentaTable.COLUMN_SUBTOTAL, detalleVenta.getSubtotal());

        return db.insert(DatabaseContract.DetalleVentaTable.TABLE_NAME, null, values);
    }
}