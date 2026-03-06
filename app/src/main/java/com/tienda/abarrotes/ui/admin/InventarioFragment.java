package com.tienda.abarrotes.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.common.adapters.MovimientoAdapter;
import com.tienda.abarrotes.ui.common.adapters.ProductoAdapter;
import com.tienda.abarrotes.viewmodel.InventarioViewModel;

public class InventarioFragment extends Fragment {

    private RecyclerView rvInventarioProductos;
    private RecyclerView rvMovimientosInventario;
    private TextView tvSinMovimientosInventario;

    private ProductoAdapter productoAdapter;
    private MovimientoAdapter movimientoAdapter;
    private InventarioViewModel inventarioViewModel;

    public InventarioFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvInventarioProductos = view.findViewById(R.id.rvInventarioProductos);
        rvMovimientosInventario = view.findViewById(R.id.rvMovimientosInventario);
        tvSinMovimientosInventario = view.findViewById(R.id.tvSinMovimientosInventario);

        rvInventarioProductos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvMovimientosInventario.setLayoutManager(new LinearLayoutManager(requireContext()));

        productoAdapter = new ProductoAdapter();
        movimientoAdapter = new MovimientoAdapter();

        rvInventarioProductos.setAdapter(productoAdapter);
        rvMovimientosInventario.setAdapter(movimientoAdapter);

        inventarioViewModel = new ViewModelProvider(this).get(InventarioViewModel.class);

        cargarInventario();
        cargarMovimientos();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (inventarioViewModel != null) {
            cargarInventario();
            cargarMovimientos();
        }
    }

    private void cargarInventario() {
        productoAdapter.setListaProductos(inventarioViewModel.listarProductosActivos());
    }

    private void cargarMovimientos() {
        movimientoAdapter.setListaMovimientos(inventarioViewModel.listarMovimientosRecientes());

        if (inventarioViewModel.listarMovimientosRecientes().isEmpty()) {
            tvSinMovimientosInventario.setVisibility(View.VISIBLE);
            rvMovimientosInventario.setVisibility(View.GONE);
        } else {
            tvSinMovimientosInventario.setVisibility(View.GONE);
            rvMovimientosInventario.setVisibility(View.VISIBLE);
        }
    }
}