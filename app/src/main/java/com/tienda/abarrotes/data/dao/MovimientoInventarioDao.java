package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.MovimientoInventario;

import java.util.ArrayList;
import java.util.List;

public class MovimientoInventarioDao {

    private final DatabaseHelper databaseHelper;

    public MovimientoInventarioDao(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long insertarMovimiento(MovimientoInventario movimiento) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.MovimientoInventarioTable.COLUMN_PRODUCTO_ID, movimiento.getProductoId());
        values.put(DatabaseContract.MovimientoInventarioTable.COLUMN_USUARIO_ID, movimiento.getUsuarioId());
        values.put(DatabaseContract.MovimientoInventarioTable.COLUMN_TIPO_MOVIMIENTO, movimiento.getTipoMovimiento());
        values.put(DatabaseContract.MovimientoInventarioTable.COLUMN_CANTIDAD, movimiento.getCantidad());
        values.put(DatabaseContract.MovimientoInventarioTable.COLUMN_FECHA, movimiento.getFecha());
        values.put(DatabaseContract.MovimientoInventarioTable.COLUMN_OBSERVACION, movimiento.getObservacion());

        return db.insert(DatabaseContract.MovimientoInventarioTable.TABLE_NAME, null, values);
    }

    public List<MovimientoInventario> listarMovimientosRecientes() {
        List<MovimientoInventario> lista = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.MovimientoInventarioTable.TABLE_NAME +
                    " ORDER BY " + DatabaseContract.MovimientoInventarioTable.COLUMN_ID + " DESC LIMIT 20";

            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                MovimientoInventario movimiento = new MovimientoInventario();
                movimiento.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoInventarioTable.COLUMN_ID)));
                movimiento.setProductoId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoInventarioTable.COLUMN_PRODUCTO_ID)));
                movimiento.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoInventarioTable.COLUMN_USUARIO_ID)));
                movimiento.setTipoMovimiento(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoInventarioTable.COLUMN_TIPO_MOVIMIENTO)));
                movimiento.setCantidad(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoInventarioTable.COLUMN_CANTIDAD)));
                movimiento.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoInventarioTable.COLUMN_FECHA)));
                movimiento.setObservacion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovimientoInventarioTable.COLUMN_OBSERVACION)));
                lista.add(movimiento);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lista;
    }
}