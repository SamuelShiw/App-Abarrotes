package com.tienda.abarrotes.ui.admin;

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
import com.tienda.abarrotes.ui.common.adapters.SeccionAdapter;
import com.tienda.abarrotes.viewmodel.SeccionViewModel;

public class SeccionesFragment extends Fragment {

    private RecyclerView rvSecciones;
    private Button btnIrRegistrarSeccion;

    private SeccionAdapter seccionAdapter;
    private SeccionViewModel seccionViewModel;

    public SeccionesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_secciones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSecciones = view.findViewById(R.id.rvSecciones);
        btnIrRegistrarSeccion = view.findViewById(R.id.btnIrRegistrarSeccion);

        rvSecciones.setLayoutManager(new LinearLayoutManager(requireContext()));
        seccionAdapter = new SeccionAdapter();
        rvSecciones.setAdapter(seccionAdapter);

        seccionViewModel = new ViewModelProvider(this).get(SeccionViewModel.class);

        btnIrRegistrarSeccion.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), RegistrarSeccionActivity.class))
        );

        cargarSecciones();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (seccionAdapter != null && seccionViewModel != null) {
            cargarSecciones();
        }
    }

    private void cargarSecciones() {
        seccionAdapter.setListaSecciones(seccionViewModel.listarSeccionesActivas());
    }
}