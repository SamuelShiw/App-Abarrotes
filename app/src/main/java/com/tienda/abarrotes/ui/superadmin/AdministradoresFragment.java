package com.tienda.abarrotes.ui.superadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.common.adapters.UsuarioAdapter;
import com.tienda.abarrotes.viewmodel.SuperAdminViewModel;

public class AdministradoresFragment extends Fragment {

    private RecyclerView rvAdministradores;
    private UsuarioAdapter usuarioAdapter;
    private SuperAdminViewModel superAdminViewModel;

    public AdministradoresFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_administradores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvAdministradores = view.findViewById(R.id.rvAdministradores);
        rvAdministradores.setLayoutManager(new LinearLayoutManager(requireContext()));

        usuarioAdapter = new UsuarioAdapter();
        rvAdministradores.setAdapter(usuarioAdapter);

        superAdminViewModel = new ViewModelProvider(this).get(SuperAdminViewModel.class);

        cargarAdministradores();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (usuarioAdapter != null && superAdminViewModel != null) {
            cargarAdministradores();
        }
    }

    private void cargarAdministradores() {
        usuarioAdapter.setListaUsuarios(superAdminViewModel.listarAdministradores());
    }
}