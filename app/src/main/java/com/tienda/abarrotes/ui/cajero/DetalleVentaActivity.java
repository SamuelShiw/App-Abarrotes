package com.tienda.abarrotes.ui.cajero;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;

public class DetalleVentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_venta);
        Toast.makeText(this, "Detalle de venta en construcción", Toast.LENGTH_SHORT).show();
    }
}