package com.tienda.abarrotes.ui.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.interfaces.OnProductoClickListener;
import com.tienda.abarrotes.ui.common.adapters.ProductoAdapter;
import com.tienda.abarrotes.viewmodel.ProductoViewModel;

public class ProductosFragment extends Fragment {

    private RecyclerView rvProductos;
    private Button btnIrRegistrarProducto;

    private ProductoAdapter productoAdapter;
    private ProductoViewModel productoViewModel;

    private OnProductoClickListener onProductoClickListener;

    public ProductosFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnProductoClickListener) {
            onProductoClickListener = (OnProductoClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " debe implementar OnProductoClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onProductoClickListener = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_productos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvProductos = view.findViewById(R.id.rvProductos);
        btnIrRegistrarProducto = view.findViewById(R.id.btnIrRegistrarProducto);

        rvProductos.setLayoutManager(new LinearLayoutManager(requireContext()));
        productoAdapter = new ProductoAdapter();
        rvProductos.setAdapter(productoAdapter);

        productoAdapter.setOnProductoClickListener(productoId -> {
            if (onProductoClickListener != null) {
                onProductoClickListener.onProductoClick(productoId);
            }
        });

        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);

        btnIrRegistrarProducto.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), RegistrarProductoActivity.class))
        );

        cargarProductos();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (productoAdapter != null && productoViewModel != null) {
            cargarProductos();
        }
    }

    private void cargarProductos() {
        productoAdapter.setListaProductos(productoViewModel.listarProductosActivos());
    }
}