package com.tienda.abarrotes.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.ProductoDao;
import com.tienda.abarrotes.data.model.Producto;

import java.util.List;

public class ProductoRepository {

    private final ProductoDao productoDao;

    public ProductoRepository(Context context) {
        this.productoDao = new ProductoDao(context);
    }

    public long insertarProducto(Producto producto) {
        return productoDao.insertarProducto(producto);
    }

    public boolean existeCodigo(String codigo) {
        return productoDao.existeCodigo(codigo);
    }

    public List<Producto> listarProductosActivos() {
        return productoDao.listarProductosActivos();
    }

    public Producto obtenerProductoPorId(int productoId) {
        return productoDao.obtenerProductoPorId(productoId);
    }

    public boolean actualizarProducto(Producto producto) {
        return productoDao.actualizarProducto(producto);
    }

    public boolean desactivarProducto(int productoId) {
        return productoDao.desactivarProducto(productoId);
    }
}