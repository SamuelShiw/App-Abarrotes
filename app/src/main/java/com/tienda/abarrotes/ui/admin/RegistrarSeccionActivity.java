package com.tienda.abarrotes.ui.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Seccion;
import com.tienda.abarrotes.viewmodel.SeccionViewModel;

public class RegistrarSeccionActivity extends AppCompatActivity {

    private EditText etNombreSeccion;
    private EditText etDescripcionSeccion;
    private Button btnGuardarSeccion;

    private SeccionViewModel seccionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_seccion);

        seccionViewModel = new ViewModelProvider(this).get(SeccionViewModel.class);

        initViews();
        setListeners();
    }

    private void initViews() {
        etNombreSeccion = findViewById(R.id.etNombreSeccion);
        etDescripcionSeccion = findViewById(R.id.etDescripcionSeccion);
        btnGuardarSeccion = findViewById(R.id.btnGuardarSeccion);
    }

    private void setListeners() {
        btnGuardarSeccion.setOnClickListener(v -> guardarSeccion());
    }

    private void guardarSeccion() {
        String nombre = etNombreSeccion.getText().toString().trim();
        String descripcion = etDescripcionSeccion.getText().toString().trim();

        String validacion = seccionViewModel.validarCampos(nombre);
        if (validacion != null) {
            Toast.makeText(this, validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        if (seccionViewModel.existeNombre(nombre)) {
            Toast.makeText(this, "La sección ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        Seccion seccion = new Seccion();
        seccion.setNombre(nombre);
        seccion.setDescripcion(descripcion);
        seccion.setEstado(1);

        long resultado = seccionViewModel.insertarSeccion(seccion);

        if (resultado > 0) {
            Toast.makeText(this, "Sección registrada correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No se pudo registrar la sección", Toast.LENGTH_SHORT).show();
        }
    }
}