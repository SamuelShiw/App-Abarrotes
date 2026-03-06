package com.tienda.abarrotes.data.db;

public final class DatabaseContract {

    private DatabaseContract() {
    }

    public static final String DATABASE_NAME = "abarrotes.db";
    public static final int DATABASE_VERSION = 1;

    public static final class RolTable {
        public static final String TABLE_NAME = "roles";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NOMBRE = "nombre";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NOMBRE + " TEXT NOT NULL UNIQUE" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class TrabajadorTable {
        public static final String TABLE_NAME = "trabajadores";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NOMBRES = "nombres";
        public static final String COLUMN_APELLIDOS = "apellidos";
        public static final String COLUMN_DNI = "dni";
        public static final String COLUMN_TELEFONO = "telefono";
        public static final String COLUMN_CARGO = "cargo";
        public static final String COLUMN_ESTADO = "estado";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NOMBRES + " TEXT NOT NULL, " +
                        COLUMN_APELLIDOS + " TEXT NOT NULL, " +
                        COLUMN_DNI + " TEXT NOT NULL UNIQUE, " +
                        COLUMN_TELEFONO + " TEXT, " +
                        COLUMN_CARGO + " TEXT NOT NULL, " +
                        COLUMN_ESTADO + " INTEGER NOT NULL DEFAULT 1" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class UsuarioTable {
        public static final String TABLE_NAME = "usuarios";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TRABAJADOR_ID = "trabajador_id";
        public static final String COLUMN_ROL_ID = "rol_id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_ESTADO = "estado";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TRABAJADOR_ID + " INTEGER NOT NULL, " +
                        COLUMN_ROL_ID + " INTEGER NOT NULL, " +
                        COLUMN_USERNAME + " TEXT NOT NULL UNIQUE, " +
                        COLUMN_PASSWORD + " TEXT NOT NULL, " +
                        COLUMN_ESTADO + " INTEGER NOT NULL DEFAULT 1, " +
                        "FOREIGN KEY (" + COLUMN_TRABAJADOR_ID + ") REFERENCES " +
                        TrabajadorTable.TABLE_NAME + "(" + TrabajadorTable.COLUMN_ID + "), " +
                        "FOREIGN KEY (" + COLUMN_ROL_ID + ") REFERENCES " +
                        RolTable.TABLE_NAME + "(" + RolTable.COLUMN_ID + ")" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class SeccionTable {
        public static final String TABLE_NAME = "secciones";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_ESTADO = "estado";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NOMBRE + " TEXT NOT NULL UNIQUE, " +
                        COLUMN_DESCRIPCION + " TEXT, " +
                        COLUMN_ESTADO + " INTEGER NOT NULL DEFAULT 1" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class EstanteTable {
        public static final String TABLE_NAME = "estantes";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SECCION_ID = "seccion_id";
        public static final String COLUMN_CODIGO = "codigo";
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_ESTADO = "estado";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_SECCION_ID + " INTEGER NOT NULL, " +
                        COLUMN_CODIGO + " TEXT NOT NULL UNIQUE, " +
                        COLUMN_DESCRIPCION + " TEXT, " +
                        COLUMN_ESTADO + " INTEGER NOT NULL DEFAULT 1, " +
                        "FOREIGN KEY (" + COLUMN_SECCION_ID + ") REFERENCES " +
                        SeccionTable.TABLE_NAME + "(" + SeccionTable.COLUMN_ID + ")" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class ProductoTable {
        public static final String TABLE_NAME = "productos";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SECCION_ID = "seccion_id";
        public static final String COLUMN_ESTANTE_ID = "estante_id";
        public static final String COLUMN_CODIGO = "codigo";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_PRECIO = "precio";
        public static final String COLUMN_STOCK_ALMACEN = "stock_almacen";
        public static final String COLUMN_STOCK_ESTANTE = "stock_estante";
        public static final String COLUMN_FECHA_VENCIMIENTO = "fecha_vencimiento";
        public static final String COLUMN_ESTADO = "estado";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_SECCION_ID + " INTEGER NOT NULL, " +
                        COLUMN_ESTANTE_ID + " INTEGER NOT NULL, " +
                        COLUMN_CODIGO + " TEXT NOT NULL UNIQUE, " +
                        COLUMN_NOMBRE + " TEXT NOT NULL, " +
                        COLUMN_DESCRIPCION + " TEXT, " +
                        COLUMN_PRECIO + " REAL NOT NULL, " +
                        COLUMN_STOCK_ALMACEN + " INTEGER NOT NULL DEFAULT 0, " +
                        COLUMN_STOCK_ESTANTE + " INTEGER NOT NULL DEFAULT 0, " +
                        COLUMN_FECHA_VENCIMIENTO + " TEXT, " +
                        COLUMN_ESTADO + " INTEGER NOT NULL DEFAULT 1, " +
                        "FOREIGN KEY (" + COLUMN_SECCION_ID + ") REFERENCES " +
                        SeccionTable.TABLE_NAME + "(" + SeccionTable.COLUMN_ID + "), " +
                        "FOREIGN KEY (" + COLUMN_ESTANTE_ID + ") REFERENCES " +
                        EstanteTable.TABLE_NAME + "(" + EstanteTable.COLUMN_ID + ")" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class MovimientoInventarioTable {
        public static final String TABLE_NAME = "movimientos_inventario";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PRODUCTO_ID = "producto_id";
        public static final String COLUMN_USUARIO_ID = "usuario_id";
        public static final String COLUMN_TIPO_MOVIMIENTO = "tipo_movimiento";
        public static final String COLUMN_CANTIDAD = "cantidad";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_OBSERVACION = "observacion";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_PRODUCTO_ID + " INTEGER NOT NULL, " +
                        COLUMN_USUARIO_ID + " INTEGER NOT NULL, " +
                        COLUMN_TIPO_MOVIMIENTO + " TEXT NOT NULL, " +
                        COLUMN_CANTIDAD + " INTEGER NOT NULL, " +
                        COLUMN_FECHA + " TEXT NOT NULL, " +
                        COLUMN_OBSERVACION + " TEXT, " +
                        "FOREIGN KEY (" + COLUMN_PRODUCTO_ID + ") REFERENCES " +
                        ProductoTable.TABLE_NAME + "(" + ProductoTable.COLUMN_ID + "), " +
                        "FOREIGN KEY (" + COLUMN_USUARIO_ID + ") REFERENCES " +
                        UsuarioTable.TABLE_NAME + "(" + UsuarioTable.COLUMN_ID + ")" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class CajaTable {
        public static final String TABLE_NAME = "cajas";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USUARIO_ID = "usuario_id";
        public static final String COLUMN_FECHA_APERTURA = "fecha_apertura";
        public static final String COLUMN_MONTO_INICIAL = "monto_inicial";
        public static final String COLUMN_FECHA_CIERRE = "fecha_cierre";
        public static final String COLUMN_MONTO_FINAL = "monto_final";
        public static final String COLUMN_ESTADO = "estado";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USUARIO_ID + " INTEGER NOT NULL, " +
                        COLUMN_FECHA_APERTURA + " TEXT NOT NULL, " +
                        COLUMN_MONTO_INICIAL + " REAL NOT NULL, " +
                        COLUMN_FECHA_CIERRE + " TEXT, " +
                        COLUMN_MONTO_FINAL + " REAL, " +
                        COLUMN_ESTADO + " TEXT NOT NULL, " +
                        "FOREIGN KEY (" + COLUMN_USUARIO_ID + ") REFERENCES " +
                        UsuarioTable.TABLE_NAME + "(" + UsuarioTable.COLUMN_ID + ")" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class VentaTable {
        public static final String TABLE_NAME = "ventas";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CAJA_ID = "caja_id";
        public static final String COLUMN_USUARIO_ID = "usuario_id";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_TOTAL = "total";
        public static final String COLUMN_TIPO_COMPROBANTE = "tipo_comprobante";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CAJA_ID + " INTEGER NOT NULL, " +
                        COLUMN_USUARIO_ID + " INTEGER NOT NULL, " +
                        COLUMN_FECHA + " TEXT NOT NULL, " +
                        COLUMN_TOTAL + " REAL NOT NULL, " +
                        COLUMN_TIPO_COMPROBANTE + " TEXT NOT NULL, " +
                        "FOREIGN KEY (" + COLUMN_CAJA_ID + ") REFERENCES " +
                        CajaTable.TABLE_NAME + "(" + CajaTable.COLUMN_ID + "), " +
                        "FOREIGN KEY (" + COLUMN_USUARIO_ID + ") REFERENCES " +
                        UsuarioTable.TABLE_NAME + "(" + UsuarioTable.COLUMN_ID + ")" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class DetalleVentaTable {
        public static final String TABLE_NAME = "detalle_venta";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_VENTA_ID = "venta_id";
        public static final String COLUMN_PRODUCTO_ID = "producto_id";
        public static final String COLUMN_CANTIDAD = "cantidad";
        public static final String COLUMN_PRECIO_UNITARIO = "precio_unitario";
        public static final String COLUMN_SUBTOTAL = "subtotal";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_VENTA_ID + " INTEGER NOT NULL, " +
                        COLUMN_PRODUCTO_ID + " INTEGER NOT NULL, " +
                        COLUMN_CANTIDAD + " INTEGER NOT NULL, " +
                        COLUMN_PRECIO_UNITARIO + " REAL NOT NULL, " +
                        COLUMN_SUBTOTAL + " REAL NOT NULL, " +
                        "FOREIGN KEY (" + COLUMN_VENTA_ID + ") REFERENCES " +
                        VentaTable.TABLE_NAME + "(" + VentaTable.COLUMN_ID + "), " +
                        "FOREIGN KEY (" + COLUMN_PRODUCTO_ID + ") REFERENCES " +
                        ProductoTable.TABLE_NAME + "(" + ProductoTable.COLUMN_ID + ")" +
                        ");";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}