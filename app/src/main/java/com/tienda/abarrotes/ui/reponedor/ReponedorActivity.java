package com.tienda.abarrotes.ui.reponedor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.auth.LoginActivity;
import com.tienda.abarrotes.ui.common.utils.SessionManager;

public class ReponedorActivity extends AppCompatActivity {

    private Button btnModuloProductosSeccion;
    private Button btnModuloCaducidad;
    private Button btnModuloFaltantes;
    private Button btnModuloReposicion;
    private Button btnCerrarSesionReponedor;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponedor);

        sessionManager = new SessionManager(this);

        btnModuloProductosSeccion = findViewById(R.id.btnModuloProductosSeccion);
        btnModuloCaducidad = findViewById(R.id.btnModuloCaducidad);
        btnModuloFaltantes = findViewById(R.id.btnModuloFaltantes);
        btnModuloReposicion = findViewById(R.id.btnModuloReposicion);
        btnCerrarSesionReponedor = findViewById(R.id.btnCerrarSesionReponedor);

        btnModuloProductosSeccion.setOnClickListener(v ->
                mostrarFragment(new ProductosSeccionFragment()));

        btnModuloCaducidad.setOnClickListener(v ->
                mostrarFragment(new CaducidadFragment()));

        btnModuloFaltantes.setOnClickListener(v ->
                mostrarFragment(new FaltantesFragment()));

        btnModuloReposicion.setOnClickListener(v ->
                mostrarFragment(new ReposicionFragment()));

        btnCerrarSesionReponedor.setOnClickListener(v -> cerrarSesion());

        if (savedInstanceState == null) {
            mostrarFragment(new ProductosSeccionFragment());
        }
    }

    private void mostrarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerReponedor, fragment)
                .commit();
    }

    private void cerrarSesion() {
        sessionManager.cerrarSesion();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}