package com.tienda.abarrotes.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseSeeder {

    private final DatabaseHelper databaseHelper;

    public DatabaseSeeder(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void seedInitialData() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        insertarRolesSiNoExisten(db);
        insertarSuperAdminSiNoExiste(db);
        insertarAdministradorInicialSiNoExiste(db);
    }

    private void insertarRolesSiNoExisten(SQLiteDatabase db) {
        insertarRolSiNoExiste(db, "SUPERADMINISTRADOR");
        insertarRolSiNoExiste(db, "ADMINISTRADOR");
        insertarRolSiNoExiste(db, "CAJERO");
        insertarRolSiNoExiste(db, "REPONEDOR");
    }

    private void insertarRolSiNoExiste(SQLiteDatabase db, String nombreRol) {
        Cursor cursor = null;

        try {
            String sql = "SELECT " + DatabaseContract.RolTable.COLUMN_ID +
                    " FROM " + DatabaseContract.RolTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.RolTable.COLUMN_NOMBRE + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{nombreRol});

            if (!cursor.moveToFirst()) {
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.RolTable.COLUMN_NOMBRE, nombreRol);
                db.insert(DatabaseContract.RolTable.TABLE_NAME, null, values);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void insertarSuperAdminSiNoExiste(SQLiteDatabase db) {
        Cursor cursorUsuario = null;

        try {
            String sqlUsuario = "SELECT " + DatabaseContract.UsuarioTable.COLUMN_ID +
                    " FROM " + DatabaseContract.UsuarioTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.UsuarioTable.COLUMN_USERNAME + " = ? LIMIT 1";

            cursorUsuario = db.rawQuery(sqlUsuario, new String[]{"superadmin"});

            if (cursorUsuario.moveToFirst()) {
                return;
            }

            long trabajadorId = insertarTrabajadorSuperAdmin(db);
            long rolId = obtenerRolIdPorNombre(db, "SUPERADMINISTRADOR");

            if (trabajadorId <= 0 || rolId <= 0) {
                return;
            }

            ContentValues usuarioValues = new ContentValues();
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_TRABAJADOR_ID, trabajadorId);
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_ROL_ID, rolId);
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_USERNAME, "superadmin");
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_PASSWORD, "123456");
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_ESTADO, 1);

            db.insert(DatabaseContract.UsuarioTable.TABLE_NAME, null, usuarioValues);

        } finally {
            if (cursorUsuario != null) {
                cursorUsuario.close();
            }
        }
    }

    private long insertarTrabajadorSuperAdmin(SQLiteDatabase db) {
        Cursor cursorTrabajador = null;

        try {
            String dniSuperAdmin = "00000001";

            String sqlTrabajador = "SELECT " + DatabaseContract.TrabajadorTable.COLUMN_ID +
                    " FROM " + DatabaseContract.TrabajadorTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.TrabajadorTable.COLUMN_DNI + " = ? LIMIT 1";

            cursorTrabajador = db.rawQuery(sqlTrabajador, new String[]{dniSuperAdmin});

            if (cursorTrabajador.moveToFirst()) {
                return cursorTrabajador.getLong(
                        cursorTrabajador.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_ID)
                );
            }

            ContentValues trabajadorValues = new ContentValues();
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_NOMBRES, "Super");
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_APELLIDOS, "Administrador");
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_DNI, dniSuperAdmin);
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_TELEFONO, "999999999");
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_CARGO, "SUPERADMINISTRADOR");
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_ESTADO, 1);

            return db.insert(DatabaseContract.TrabajadorTable.TABLE_NAME, null, trabajadorValues);

        } finally {
            if (cursorTrabajador != null) {
                cursorTrabajador.close();
            }
        }
    }

    private void insertarAdministradorInicialSiNoExiste(SQLiteDatabase db) {
        Cursor cursorUsuario = null;

        try {
            String sqlUsuario = "SELECT " + DatabaseContract.UsuarioTable.COLUMN_ID +
                    " FROM " + DatabaseContract.UsuarioTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.UsuarioTable.COLUMN_USERNAME + " = ? LIMIT 1";

            cursorUsuario = db.rawQuery(sqlUsuario, new String[]{"admin"});

            if (cursorUsuario.moveToFirst()) {
                return;
            }

            long trabajadorId = insertarTrabajadorAdministradorInicial(db);
            long rolId = obtenerRolIdPorNombre(db, "ADMINISTRADOR");

            if (trabajadorId <= 0 || rolId <= 0) {
                return;
            }

            ContentValues usuarioValues = new ContentValues();
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_TRABAJADOR_ID, trabajadorId);
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_ROL_ID, rolId);
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_USERNAME, "admin");
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_PASSWORD, "123456");
            usuarioValues.put(DatabaseContract.UsuarioTable.COLUMN_ESTADO, 1);

            db.insert(DatabaseContract.UsuarioTable.TABLE_NAME, null, usuarioValues);

        } finally {
            if (cursorUsuario != null) {
                cursorUsuario.close();
            }
        }
    }

    private long insertarTrabajadorAdministradorInicial(SQLiteDatabase db) {
        Cursor cursorTrabajador = null;

        try {
            String dniAdministrador = "00000002";

            String sqlTrabajador = "SELECT " + DatabaseContract.TrabajadorTable.COLUMN_ID +
                    " FROM " + DatabaseContract.TrabajadorTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.TrabajadorTable.COLUMN_DNI + " = ? LIMIT 1";

            cursorTrabajador = db.rawQuery(sqlTrabajador, new String[]{dniAdministrador});

            if (cursorTrabajador.moveToFirst()) {
                return cursorTrabajador.getLong(
                        cursorTrabajador.getColumnIndexOrThrow(DatabaseContract.TrabajadorTable.COLUMN_ID)
                );
            }

            ContentValues trabajadorValues = new ContentValues();
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_NOMBRES, "Admin");
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_APELLIDOS, "Principal");
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_DNI, dniAdministrador);
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_TELEFONO, "988888888");
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_CARGO, "ADMINISTRADOR");
            trabajadorValues.put(DatabaseContract.TrabajadorTable.COLUMN_ESTADO, 1);

            return db.insert(DatabaseContract.TrabajadorTable.TABLE_NAME, null, trabajadorValues);

        } finally {
            if (cursorTrabajador != null) {
                cursorTrabajador.close();
            }
        }
    }

    private long obtenerRolIdPorNombre(SQLiteDatabase db, String nombreRol) {
        Cursor cursor = null;

        try {
            String sql = "SELECT " + DatabaseContract.RolTable.COLUMN_ID +
                    " FROM " + DatabaseContract.RolTable.TABLE_NAME +
                    " WHERE " + DatabaseContract.RolTable.COLUMN_NOMBRE + " = ? LIMIT 1";

            cursor = db.rawQuery(sql, new String[]{nombreRol});

            if (cursor.moveToFirst()) {
                return cursor.getLong(
                        cursor.getColumnIndexOrThrow(DatabaseContract.RolTable.COLUMN_ID)
                );
            }

            return -1;

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}