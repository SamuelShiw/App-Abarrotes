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

import com.tienda.abarrotes.R;

public class UsuariosAdminFragment extends Fragment {

    private Button btnIrRegistrarUsuario;

    public UsuariosAdminFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuarios_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnIrRegistrarUsuario = view.findViewById(R.id.btnIrRegistrarUsuario);

        btnIrRegistrarUsuario.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), RegistrarUsuarioActivity.class))
        );
    }
}