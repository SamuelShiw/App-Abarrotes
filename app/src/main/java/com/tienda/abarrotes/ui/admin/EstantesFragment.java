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
import com.tienda.abarrotes.ui.common.adapters.EstanteAdapter;
import com.tienda.abarrotes.viewmodel.EstanteViewModel;

public class EstantesFragment extends Fragment {

    private RecyclerView rvEstantes;
    private Button btnIrRegistrarEstante;

    private EstanteAdapter estanteAdapter;
    private EstanteViewModel estanteViewModel;

    public EstantesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estantes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvEstantes = view.findViewById(R.id.rvEstantes);
        btnIrRegistrarEstante = view.findViewById(R.id.btnIrRegistrarEstante);

        rvEstantes.setLayoutManager(new LinearLayoutManager(requireContext()));
        estanteAdapter = new EstanteAdapter();
        rvEstantes.setAdapter(estanteAdapter);

        estanteViewModel = new ViewModelProvider(this).get(EstanteViewModel.class);

        btnIrRegistrarEstante.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), RegistrarEstanteActivity.class))
        );

        cargarEstantes();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (estanteAdapter != null && estanteViewModel != null) {
            cargarEstantes();
        }
    }

    private void cargarEstantes() {
        estanteAdapter.setListaEstantes(estanteViewModel.listarEstantesActivos());
    }
}