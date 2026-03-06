package com.tienda.abarrotes.ui.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;

public class EditarSeccionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_seccion);
        Toast.makeText(this, "Editar sección en construcción", Toast.LENGTH_SHORT).show();
    }
}