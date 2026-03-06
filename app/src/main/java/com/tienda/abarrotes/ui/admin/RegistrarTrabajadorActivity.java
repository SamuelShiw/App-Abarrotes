package com.tienda.abarrotes.ui.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Trabajador;
import com.tienda.abarrotes.viewmodel.TrabajadorViewModel;

public class RegistrarTrabajadorActivity extends AppCompatActivity {

    private EditText etNombresTrabajador;
    private EditText etApellidosTrabajador;
    private EditText etDniTrabajador;
    private EditText etTelefonoTrabajador;
    private EditText etCargoTrabajador;
    private Button btnGuardarTrabajador;

    private TrabajadorViewModel trabajadorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_trabajador);

        trabajadorViewModel = new ViewModelProvider(this).get(TrabajadorViewModel.class);

        initViews();
        setListeners();
    }

    private void initViews() {
        etNombresTrabajador = findViewById(R.id.etNombresTrabajador);
        etApellidosTrabajador = findViewById(R.id.etApellidosTrabajador);
        etDniTrabajador = findViewById(R.id.etDniTrabajador);
        etTelefonoTrabajador = findViewById(R.id.etTelefonoTrabajador);
        etCargoTrabajador = findViewById(R.id.etCargoTrabajador);
        btnGuardarTrabajador = findViewById(R.id.btnGuardarTrabajador);
    }

    private void setListeners() {
        btnGuardarTrabajador.setOnClickListener(v -> guardarTrabajador());
    }

    private void guardarTrabajador() {
        String nombres = etNombresTrabajador.getText().toString().trim();
        String apellidos = etApellidosTrabajador.getText().toString().trim();
        String dni = etDniTrabajador.getText().toString().trim();
        String telefono = etTelefonoTrabajador.getText().toString().trim();
        String cargo = etCargoTrabajador.getText().toString().trim();

        String validacion = trabajadorViewModel.validarCampos(nombres, apellidos, dni, cargo);
        if (validacion != null) {
            Toast.makeText(this, validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        if (trabajadorViewModel.existeDni(dni)) {
            Toast.makeText(this, "El DNI ya está registrado", Toast.LENGTH_SHORT).show();
            return;
        }

        Trabajador trabajador = new Trabajador();
        trabajador.setNombres(nombres);
        trabajador.setApellidos(apellidos);
        trabajador.setDni(dni);
        trabajador.setTelefono(telefono);
        trabajador.setCargo(cargo);
        trabajador.setEstado(1);

        long resultado = trabajadorViewModel.insertarTrabajador(trabajador);

        if (resultado > 0) {
            Toast.makeText(this, "Trabajador registrado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No se pudo registrar el trabajador", Toast.LENGTH_SHORT).show();
        }
    }
}