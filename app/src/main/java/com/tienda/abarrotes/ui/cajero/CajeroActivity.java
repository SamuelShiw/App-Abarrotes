package com.tienda.abarrotes.ui.cajero;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.auth.LoginActivity;
import com.tienda.abarrotes.ui.common.utils.SessionManager;

public class CajeroActivity extends AppCompatActivity {

    private Button btnModuloAperturaCaja;
    private Button btnModuloVenta;
    private Button btnModuloCierreCaja;
    private Button btnCerrarSesionCajero;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajero);

        sessionManager = new SessionManager(this);

        btnModuloAperturaCaja = findViewById(R.id.btnModuloAperturaCaja);
        btnModuloVenta = findViewById(R.id.btnModuloVenta);
        btnModuloCierreCaja = findViewById(R.id.btnModuloCierreCaja);
        btnCerrarSesionCajero = findViewById(R.id.btnCerrarSesionCajero);

        btnModuloAperturaCaja.setOnClickListener(v ->
                mostrarFragment(new AperturaCajaFragment()));

        btnModuloVenta.setOnClickListener(v ->
                mostrarFragment(new VentaFragment()));

        btnModuloCierreCaja.setOnClickListener(v ->
                mostrarFragment(new CierreCajaFragment()));

        btnCerrarSesionCajero.setOnClickListener(v -> cerrarSesion());

        if (savedInstanceState == null) {
            mostrarFragment(new AperturaCajaFragment());
        }
    }

    private void mostrarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerCajero, fragment)
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