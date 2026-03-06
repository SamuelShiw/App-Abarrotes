package com.tienda.abarrotes.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.Producto;
import com.tienda.abarrotes.repository.InventarioRepository;

import java.util.List;

public class ReponedorViewModel extends AndroidViewModel {

    private final InventarioRepository inventarioRepository;

    public ReponedorViewModel(@NonNull Application application) {
        super(application);
        inventarioRepository = new InventarioRepository(application);
    }

    public List<Producto> listarProductosActivos() {
        return inventarioRepository.listarProductosActivos();
    }

    public String validarCantidad(String cantidadTexto) {
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

        return null;
    }

    public long registrarReposicion(int usuarioId, int productoId, int cantidad, String fecha) {
        return inventarioRepository.registrarReposicion(usuarioId, productoId, cantidad, fecha);
    }
}