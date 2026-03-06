package com.tienda.abarrotes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tienda.abarrotes.data.model.MovimientoInventario;
import com.tienda.abarrotes.data.model.Producto;
import com.tienda.abarrotes.repository.InventarioRepository;

import java.util.List;

public class InventarioViewModel extends AndroidViewModel {

    private final InventarioRepository inventarioRepository;

    public InventarioViewModel(@NonNull Application application) {
        super(application);
        inventarioRepository = new InventarioRepository(application);
    }

    public List<Producto> listarProductosActivos() {
        return inventarioRepository.listarProductosActivos();
    }

    public List<MovimientoInventario> listarMovimientosRecientes() {
        return inventarioRepository.listarMovimientosRecientes();
    }
}