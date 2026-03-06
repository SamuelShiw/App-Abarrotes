package com.tienda.abarrotes.ui.cajero;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tienda.abarrotes.R;

public class CajeroActivity extends AppCompatActivity {

    private Button btnModuloAperturaCaja;
    private Button btnModuloVenta;
    private Button btnModuloCierreCaja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cajero);

        btnModuloAperturaCaja = findViewById(R.id.btnModuloAperturaCaja);
        btnModuloVenta = findViewById(R.id.btnModuloVenta);
        btnModuloCierreCaja = findViewById(R.id.btnModuloCierreCaja);

        btnModuloAperturaCaja.setOnClickListener(v ->
                mostrarFragment(new AperturaCajaFragment()));

        btnModuloVenta.setOnClickListener(v ->
                mostrarFragment(new VentaFragment()));

        btnModuloCierreCaja.setOnClickListener(v ->
                mostrarFragment(new CierreCajaFragment()));

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
}