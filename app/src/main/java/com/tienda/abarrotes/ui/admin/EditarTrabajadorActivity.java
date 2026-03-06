package com.tienda.abarrotes.ui.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;

public class EditarTrabajadorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_trabajador);
        Toast.makeText(this, "Editar trabajador en construcción", Toast.LENGTH_SHORT).show();
    }
}