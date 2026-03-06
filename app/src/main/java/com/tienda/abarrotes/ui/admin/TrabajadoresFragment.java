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
import com.tienda.abarrotes.ui.common.adapters.TrabajadorAdapter;
import com.tienda.abarrotes.viewmodel.TrabajadorViewModel;

public class TrabajadoresFragment extends Fragment {

    private RecyclerView rvTrabajadores;
    private Button btnIrRegistrarTrabajador;

    private TrabajadorAdapter trabajadorAdapter;
    private TrabajadorViewModel trabajadorViewModel;

    public TrabajadoresFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trabajadores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTrabajadores = view.findViewById(R.id.rvTrabajadores);
        btnIrRegistrarTrabajador = view.findViewById(R.id.btnIrRegistrarTrabajador);

        rvTrabajadores.setLayoutManager(new LinearLayoutManager(requireContext()));
        trabajadorAdapter = new TrabajadorAdapter();
        rvTrabajadores.setAdapter(trabajadorAdapter);

        trabajadorViewModel = new ViewModelProvider(this).get(TrabajadorViewModel.class);

        btnIrRegistrarTrabajador.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), RegistrarTrabajadorActivity.class))
        );

        cargarTrabajadores();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (trabajadorAdapter != null && trabajadorViewModel != null) {
            cargarTrabajadores();
        }
    }

    private void cargarTrabajadores() {
        trabajadorAdapter.setListaTrabajadores(trabajadorViewModel.listarTrabajadoresActivos());
    }
}