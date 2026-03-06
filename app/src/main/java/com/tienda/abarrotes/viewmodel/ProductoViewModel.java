package com.tienda.abarrotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.Producto;
import com.tienda.abarrotes.repository.ProductoRepository;

import java.util.List;

public class ProductoViewModel extends AndroidViewModel {

    private final ProductoRepository productoRepository;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
        productoRepository = new ProductoRepository(application);
    }

    public List<Producto> listarProductosActivos() {
        return productoRepository.listarProductosActivos();
    }

    public boolean existeCodigo(String codigo) {
        return productoRepository.existeCodigo(codigo);
    }

    public long insertarProducto(Producto producto) {
        return productoRepository.insertarProducto(producto);
    }

    public Producto obtenerProductoPorId(int productoId) {
        return productoRepository.obtenerProductoPorId(productoId);
    }

    public boolean actualizarProducto(Producto producto) {
        return productoRepository.actualizarProducto(producto);
    }

    public boolean desactivarProducto(int productoId) {
        return productoRepository.desactivarProducto(productoId);
    }

    public String validarCampos(String codigo, String nombre, String precioTexto,
                                String stockAlmacenTexto, String stockEstanteTexto) {
        if (TextUtils.isEmpty(codigo)) {
            return "Ingrese el código";
        }

        if (TextUtils.isEmpty(nombre)) {
            return "Ingrese el nombre";
        }

        if (TextUtils.isEmpty(precioTexto)) {
            return "Ingrese el precio";
        }

        if (TextUtils.isEmpty(stockAlmacenTexto)) {
            return "Ingrese el stock de almacén";
        }

        if (TextUtils.isEmpty(stockEstanteTexto)) {
            return "Ingrese el stock de estante";
        }

        try {
            double precio = Double.parseDouble(precioTexto);
            if (precio <= 0) {
                return "El precio debe ser mayor a 0";
            }
        } catch (Exception e) {
            return "Precio inválido";
        }

        try {
            int stockAlmacen = Integer.parseInt(stockAlmacenTexto);
            if (stockAlmacen < 0) {
                return "El stock de almacén no puede ser negativo";
            }
        } catch (Exception e) {
            return "Stock de almacén inválido";
        }

        try {
            int stockEstante = Integer.parseInt(stockEstanteTexto);
            if (stockEstante < 0) {
                return "El stock de estante no puede ser negativo";
            }
        } catch (Exception e) {
            return "Stock de estante inválido";
        }

        return null;
    }
}