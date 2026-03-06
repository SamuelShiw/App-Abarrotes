package com.tienda.abarrotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.Producto;
import com.tienda.abarrotes.repository.ProductoRepository;
import com.tienda.abarrotes.repository.VentaRepository;

import java.util.ArrayList;
import java.util.List;

public class VentaViewModel extends AndroidViewModel {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public VentaViewModel(@NonNull Application application) {
        super(application);
        ventaRepository = new VentaRepository(application);
        productoRepository = new ProductoRepository(application);
    }

    public List<Producto> listarProductosActivos() {
        return productoRepository.listarProductosActivos();
    }

    public String validarCampos(String cantidadTexto, String tipoComprobante) {
        if (TextUtils.isEmpty(cantidadTexto)) {
            return "Ingrese la cantidad";
        }

        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            if (cantidad <= 0) {
                return "La cantidad debe ser mayor a 0";
            }
        } catch (Exception e) {
            return "Cantidad inválida";
        }

        if (TextUtils.isEmpty(tipoComprobante)) {
            return "Seleccione tipo de comprobante";
        }

        return null;
    }

    public boolean existeCajaAbierta(int usuarioId) {
        return ventaRepository.existeCajaAbierta(usuarioId);
    }

    public long registrarVentaSimple(int usuarioId, int productoId, int cantidad, String fecha, String tipoComprobante) {
        return ventaRepository.registrarVentaSimple(usuarioId, productoId, cantidad, fecha, tipoComprobante);
    }

    public long registrarVentaMultiple(int usuarioId,
                                       List<Integer> productoIds,
                                       List<Integer> cantidades,
                                       String fecha,
                                       String tipoComprobante) {
        return ventaRepository.registrarVentaMultiple(usuarioId, productoIds, cantidades, fecha, tipoComprobante);
    }

    public List<String> obtenerTiposComprobante() {
        List<String> tipos = new ArrayList<>();
        tipos.add("BOLETA");
        tipos.add("FACTURA");
        return tipos;
    }
}