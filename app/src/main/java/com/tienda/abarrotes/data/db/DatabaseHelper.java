package com.tienda.abarrotes.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.RolTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.TrabajadorTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.UsuarioTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.SeccionTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.EstanteTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.ProductoTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.MovimientoInventarioTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.CajaTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.VentaTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.DetalleVentaTable.CREATE_TABLE);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.DetalleVentaTable.DROP_TABLE);
        db.execSQL(DatabaseContract.VentaTable.DROP_TABLE);
        db.execSQL(DatabaseContract.CajaTable.DROP_TABLE);
        db.execSQL(DatabaseContract.MovimientoInventarioTable.DROP_TABLE);
        db.execSQL(DatabaseContract.ProductoTable.DROP_TABLE);
        db.execSQL(DatabaseContract.EstanteTable.DROP_TABLE);
        db.execSQL(DatabaseContract.SeccionTable.DROP_TABLE);
        db.execSQL(DatabaseContract.UsuarioTable.DROP_TABLE);
        db.execSQL(DatabaseContract.TrabajadorTable.DROP_TABLE);
        db.execSQL(DatabaseContract.RolTable.DROP_TABLE);
        onCreate(db);
    }
}