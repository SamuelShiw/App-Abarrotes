package com.tienda.abarrotes.ui.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;

public class EditarEstanteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_estante);
        Toast.makeText(this, "Editar estante en construcción", Toast.LENGTH_SHORT).show();
    }
}