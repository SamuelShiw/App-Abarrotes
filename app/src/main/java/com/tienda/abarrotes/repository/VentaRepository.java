package com.tienda.abarrotes.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.CajaDao;
import com.tienda.abarrotes.data.dao.DetalleVentaDao;
import com.tienda.abarrotes.data.dao.VentaDao;
import com.tienda.abarrotes.data.model.DetalleVenta;
import com.tienda.abarrotes.data.model.MovimientoInventario;
import com.tienda.abarrotes.data.model.Producto;
import com.tienda.abarrotes.data.model.Venta;

import java.util.List;

public class VentaRepository {

    private final CajaDao cajaDao;
    private final VentaDao ventaDao;
    private final DetalleVentaDao detalleVentaDao;
    private final InventarioRepository inventarioRepository;

    public VentaRepository(Context context) {
        this.cajaDao = new CajaDao(context);
        this.ventaDao = new VentaDao(context);
        this.detalleVentaDao = new DetalleVentaDao(context);
        this.inventarioRepository = new InventarioRepository(context);
    }

    public boolean existeCajaAbierta(int usuarioId) {
        return cajaDao.existeCajaAbierta(usuarioId);
    }

    public int obtenerCajaAbiertaId(int usuarioId) {
        return cajaDao.obtenerCajaAbiertaId(usuarioId);
    }

    public Producto obtenerProductoPorId(int productoId) {
        return inventarioRepository.obtenerProductoPorId(productoId);
    }

    public long registrarVentaSimple(int usuarioId, int productoId, int cantidad, String fecha, String tipoComprobante) {
        java.util.ArrayList<Integer> productoIds = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> cantidades = new java.util.ArrayList<>();

        productoIds.add(productoId);
        cantidades.add(cantidad);

        return registrarVentaMultiple(usuarioId, productoIds, cantidades, fecha, tipoComprobante);
    }

    public long registrarVentaMultiple(int usuarioId,
                                       List<Integer> productoIds,
                                       List<Integer> cantidades,
                                       String fecha,
                                       String tipoComprobante) {

        int cajaId = cajaDao.obtenerCajaAbiertaId(usuarioId);
        if (cajaId <= 0) {
            return -1;
        }

        if (productoIds == null || cantidades == null || productoIds.isEmpty() || cantidades.isEmpty()) {
            return -2;
        }

        if (productoIds.size() != cantidades.size()) {
            return -3;
        }

        double totalVenta = 0.0;

        for (int i = 0; i < productoIds.size(); i++) {
            Producto producto = inventarioRepository.obtenerProductoPorId(productoIds.get(i));
            int cantidad = cantidades.get(i);

            if (producto == null) {
                return -4;
            }

            if (cantidad <= 0) {
                return -5;
            }

            if (producto.getStockEstante() < cantidad) {
                return -6;
            }

            totalVenta += producto.getPrecio() * cantidad;
        }

        Venta venta = new Venta();
        venta.setCajaId(cajaId);
        venta.setUsuarioId(usuarioId);
        venta.setFecha(fecha);
        venta.setTotal(totalVenta);
        venta.setTipoComprobante(tipoComprobante);

        long ventaId = ventaDao.insertarVenta(venta);
        if (ventaId <= 0) {
            return -7;
        }

        for (int i = 0; i < productoIds.size(); i++) {
            int productoId = productoIds.get(i);
            int cantidad = cantidades.get(i);

            Producto producto = inventarioRepository.obtenerProductoPorId(productoId);
            if (producto == null) {
                return -8;
            }

            double subtotal = producto.getPrecio() * cantidad;

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setVentaId((int) ventaId);
            detalleVenta.setProductoId(productoId);
            detalleVenta.setCantidad(cantidad);
            detalleVenta.setPrecioUnitario(producto.getPrecio());
            detalleVenta.setSubtotal(subtotal);

            long detalleId = detalleVentaDao.insertarDetalleVenta(detalleVenta);
            if (detalleId <= 0) {
                return -9;
            }

            int nuevoStockEstante = producto.getStockEstante() - cantidad;
            boolean stockActualizado = inventarioRepository.actualizarStockEstante(productoId, nuevoStockEstante);
            if (!stockActualizado) {
                return -10;
            }

            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setProductoId(productoId);
            movimiento.setUsuarioId(usuarioId);
            movimiento.setTipoMovimiento("VENTA");
            movimiento.setCantidad(cantidad);
            movimiento.setFecha(fecha);
            movimiento.setObservacion("Salida por venta - " + tipoComprobante);

            inventarioRepository.insertarMovimiento(movimiento);
        }

        return ventaId;
    }
}