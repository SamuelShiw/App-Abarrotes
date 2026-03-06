package com.tienda.abarrotes.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tienda.abarrotes.data.db.DatabaseContract;
import com.tienda.abarrotes.data.db.DatabaseHelper;
import com.tienda.abarrotes.data.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoDao {

    private final DatabaseHelper databaseHelper;

    public ProductoDao(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public long insertarProducto(Producto producto) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.ProductoTable.COLUMN_SECCION_ID, producto.getSeccionId());
        values.put(DatabaseContract.ProductoTable.COLUMN_ESTANTE_ID, producto.getEstanteId());
        values.put(DatabaseContract.ProductoTable.COLUMN_CODIGO, producto.getCodigo());
        values.put(DatabaseContract.ProductoTable.COLUMN_NOMBRE, producto.getNombre());
        values.put(DatabaseContract.ProductoTable.COLUMN_DESCRIPCION, producto.getDescripcion());
        values.put(DatabaseContract.ProductoTable.COLUMN_PRECIO, producto.getPrecio());
        values.put(DatabaseContract.ProductoTable.COLUMN_STOCK_ALMACEN, producto.getStockAlmacen());
        values.put(DatabaseContract.ProductoTable.COLUMN_STOCK_ESTANTE, producto.getStockEstante());
        values.put(DatabaseContract.ProductoTable.COLUMN_FECHA_VENCIMIENTO, producto.getFechaVencimiento());
        values.put(DatabaseContract.ProductoTable.COLUMN_ESTADO, producto.getEstado());

        return db.insert(DatabaseContract.ProductoTable.TABLE_NAME, null, values);
    }

    public boolean existeCodigo(String codigo) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT " + DatabaseContract.ProductoTable.COLUMN_ID +
                    " FROM " + DatabaseContract.ProductoTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.ProductoTable.COLUMN_CODIGO + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{codigo});
            return cursor.moveToFirst();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Producto obtenerProductoPorId(int productoId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        Producto producto = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.ProductoTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.ProductoTable.COLUMN_ID + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{String.valueOf(productoId)});

            if (cursor.moveToFirst()) {
                producto = new Producto();
                producto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_ID)));
                producto.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_SECCION_ID)));
                producto.setEstanteId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_ESTANTE_ID)));
                producto.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_CODIGO)));
                producto.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_NOMBRE)));
                producto.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_DESCRIPCION)));
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_PRECIO)));
                producto.setStockAlmacen(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_STOCK_ALMACEN)));
                producto.setStockEstante(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_STOCK_ESTANTE)));
                producto.setFechaVencimiento(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_FECHA_VENCIMIENTO)));
                producto.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_ESTADO)));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return producto;
    }

    public boolean actualizarProducto(Producto producto) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.ProductoTable.COLUMN_SECCION_ID, producto.getSeccionId());
        values.put(DatabaseContract.ProductoTable.COLUMN_ESTANTE_ID, producto.getEstanteId());
        values.put(DatabaseContract.ProductoTable.COLUMN_CODIGO, producto.getCodigo());
        values.put(DatabaseContract.ProductoTable.COLUMN_NOMBRE, producto.getNombre());
        values.put(DatabaseContract.ProductoTable.COLUMN_DESCRIPCION, producto.getDescripcion());
        values.put(DatabaseContract.ProductoTable.COLUMN_PRECIO, producto.getPrecio());
        values.put(DatabaseContract.ProductoTable.COLUMN_STOCK_ALMACEN, producto.getStockAlmacen());
        values.put(DatabaseContract.ProductoTable.COLUMN_STOCK_ESTANTE, producto.getStockEstante());
        values.put(DatabaseContract.ProductoTable.COLUMN_FECHA_VENCIMIENTO, producto.getFechaVencimiento());

        int filas = db.update(
                DatabaseContract.ProductoTable.TABLE_NAME,
                values,
                DatabaseContract.ProductoTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(producto.getId())}
        );

        return filas > 0;
    }

    public boolean desactivarProducto(int productoId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ProductoTable.COLUMN_ESTADO, 0);

        int filas = db.update(
                DatabaseContract.ProductoTable.TABLE_NAME,
                values,
                DatabaseContract.ProductoTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(productoId)}
        );

        return filas > 0;
    }

    public boolean actualizarStockEstante(int productoId, int nuevoStockEstante) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ProductoTable.COLUMN_STOCK_ESTANTE, nuevoStockEstante);

        int filas = db.update(
                DatabaseContract.ProductoTable.TABLE_NAME,
                values,
                DatabaseContract.ProductoTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(productoId)}
        );

        return filas > 0;
    }

    public boolean actualizarStocks(int productoId, int nuevoStockAlmacen, int nuevoStockEstante) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ProductoTable.COLUMN_STOCK_ALMACEN, nuevoStockAlmacen);
        values.put(DatabaseContract.ProductoTable.COLUMN_STOCK_ESTANTE, nuevoStockEstante);

        int filas = db.update(
                DatabaseContract.ProductoTable.TABLE_NAME,
                values,
                DatabaseContract.ProductoTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(productoId)}
        );

        return filas > 0;
    }

    public List<Producto> listarProductosActivos() {
        List<Producto> lista = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT * FROM " + DatabaseContract.ProductoTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.ProductoTable.COLUMN_ESTADO + " = 1" +
                    " ORDER BY " + DatabaseContract.ProductoTable.COLUMN_NOMBRE + " ASC";

            cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                Producto producto = new Producto();
                producto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_ID)));
                producto.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_SECCION_ID)));
                producto.setEstanteId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_ESTANTE_ID)));
                producto.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_CODIGO)));
                producto.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_NOMBRE)));
                producto.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_DESCRIPCION)));
                producto.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_PRECIO)));
                producto.setStockAlmacen(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_STOCK_ALMACEN)));
                producto.setStockEstante(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_STOCK_ESTANTE)));
                producto.setFechaVencimiento(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_FECHA_VENCIMIENTO)));
                producto.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ProductoTable.COLUMN_ESTADO)));
                lista.add(producto);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lista;
    }
}