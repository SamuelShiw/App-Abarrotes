package com.tienda.abarrotes.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.MovimientoInventarioDao;
import com.tienda.abarrotes.data.dao.ProductoDao;
import com.tienda.abarrotes.data.model.MovimientoInventario;
import com.tienda.abarrotes.data.model.Producto;

import java.util.List;

public class InventarioRepository {

    private final ProductoDao productoDao;
    private final MovimientoInventarioDao movimientoInventarioDao;

    public InventarioRepository(Context context) {
        this.productoDao = new ProductoDao(context);
        this.movimientoInventarioDao = new MovimientoInventarioDao(context);
    }

    public List<Producto> listarProductosActivos() {
        return productoDao.listarProductosActivos();
    }

    public Producto obtenerProductoPorId(int productoId) {
        return productoDao.obtenerProductoPorId(productoId);
    }

    public boolean actualizarStockEstante(int productoId, int nuevoStockEstante) {
        return productoDao.actualizarStockEstante(productoId, nuevoStockEstante);
    }

    public boolean actualizarStocks(int productoId, int nuevoStockAlmacen, int nuevoStockEstante) {
        return productoDao.actualizarStocks(productoId, nuevoStockAlmacen, nuevoStockEstante);
    }

    public List<MovimientoInventario> listarMovimientosRecientes() {
        return movimientoInventarioDao.listarMovimientosRecientes();
    }

    public long insertarMovimiento(MovimientoInventario movimientoInventario) {
        return movimientoInventarioDao.insertarMovimiento(movimientoInventario);
    }

    public long registrarReposicion(int usuarioId, int productoId, int cantidad, String fecha) {
        Producto producto = productoDao.obtenerProductoPorId(productoId);

        if (producto == null) {
            return -1;
        }

        if (cantidad <= 0) {
            return -2;
        }

        if (producto.getStockAlmacen() < cantidad) {
            return -3;
        }

        int nuevoStockAlmacen = producto.getStockAlmacen() - cantidad;
        int nuevoStockEstante = producto.getStockEstante() + cantidad;

        boolean actualizado = productoDao.actualizarStocks(productoId, nuevoStockAlmacen, nuevoStockEstante);
        if (!actualizado) {
            return -4;
        }

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProductoId(productoId);
        movimiento.setUsuarioId(usuarioId);
        movimiento.setTipoMovimiento("REPOSICION");
        movimiento.setCantidad(cantidad);
        movimiento.setFecha(fecha);
        movimiento.setObservacion("Movimiento de almacén a estante");

        long movimientoId = movimientoInventarioDao.insertarMovimiento(movimiento);
        if (movimientoId <= 0) {
            return -5;
        }

        return movimientoId;
    }
}