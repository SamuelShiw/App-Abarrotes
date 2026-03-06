package com.tienda.abarrotes.ui.superadmin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.auth.LoginActivity;
import com.tienda.abarrotes.ui.common.utils.SessionManager;
import com.tienda.abarrotes.ui.admin.RegistrarUsuarioActivity;

public class SuperAdminActivity extends AppCompatActivity {

    private TextView tvUsuario;
    private TextView tvRol;

    private Button btnCerrarSesion;
    private Button btnRegistrarAdministrador;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);

        sessionManager = new SessionManager(this);

        tvUsuario = findViewById(R.id.tvUsuario);
        tvRol = findViewById(R.id.tvRol);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnRegistrarAdministrador = findViewById(R.id.btnRegistrarAdministrador);

        tvUsuario.setText("Usuario: " + sessionManager.getUsername());
        tvRol.setText("Rol: " + sessionManager.getRolNombre());

        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());

        btnRegistrarAdministrador.setOnClickListener(v ->
                startActivity(new Intent(this, RegistrarUsuarioActivity.class))
        );

        if (savedInstanceState == null) {
            mostrarFragment(new AdministradoresFragment());
        }
    }

    private void mostrarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void cerrarSesion() {
        sessionManager.cerrarSesion();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}