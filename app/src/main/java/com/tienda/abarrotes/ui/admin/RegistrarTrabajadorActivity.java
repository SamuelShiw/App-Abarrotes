package com.tienda.abarrotes.ui.admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Trabajador;
import com.tienda.abarrotes.ui.common.utils.SessionManager;
import com.tienda.abarrotes.viewmodel.TrabajadorViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegistrarTrabajadorActivity extends AppCompatActivity {

    private EditText etNombresTrabajador;
    private EditText etApellidosTrabajador;
    private EditText etDniTrabajador;
    private EditText etTelefonoTrabajador;
    private Spinner spCargoTrabajador;
    private Button btnGuardarTrabajador;

    private TrabajadorViewModel trabajadorViewModel;
    private SessionManager sessionManager;

    private final List<String> cargosDisponibles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_trabajador);

        trabajadorViewModel = new ViewModelProvider(this).get(TrabajadorViewModel.class);
        sessionManager = new SessionManager(this);

        initViews();
        cargarCargosSegunRol();
        setListeners();
    }

    private void initViews() {
        etNombresTrabajador = findViewById(R.id.etNombresTrabajador);
        etApellidosTrabajador = findViewById(R.id.etApellidosTrabajador);
        etDniTrabajador = findViewById(R.id.etDniTrabajador);
        etTelefonoTrabajador = findViewById(R.id.etTelefonoTrabajador);
        spCargoTrabajador = findViewById(R.id.etCargoTrabajador);
        btnGuardarTrabajador = findViewById(R.id.btnGuardarTrabajador);
    }

    private void cargarCargosSegunRol() {
        cargosDisponibles.clear();
        cargosDisponibles.add("Seleccione un cargo");

        String rolLogueado = sessionManager.getRolNombre();

        if ("SUPERADMINISTRADOR".equalsIgnoreCase(rolLogueado)) {
            cargosDisponibles.add("ADMINISTRADOR");
            cargosDisponibles.add("CAJERO");
            cargosDisponibles.add("REPONEDOR");
        } else if ("ADMINISTRADOR".equalsIgnoreCase(rolLogueado)) {
            cargosDisponibles.add("CAJERO");
            cargosDisponibles.add("REPONEDOR");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                cargosDisponibles
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCargoTrabajador.setAdapter(adapter);
    }

    private void setListeners() {
        btnGuardarTrabajador.setOnClickListener(v -> guardarTrabajador());
    }

    private void guardarTrabajador() {
        String nombres = etNombresTrabajador.getText().toString().trim();
        String apellidos = etApellidosTrabajador.getText().toString().trim();
        String dni = etDniTrabajador.getText().toString().trim();
        String telefono = etTelefonoTrabajador.getText().toString().trim();
        String cargo = obtenerCargoSeleccionado();

        String rolLogueado = sessionManager.getRolNombre();

        String validacion = trabajadorViewModel.validarCampos(
                nombres,
                apellidos,
                dni,
                telefono,
                cargo,
                rolLogueado
        );

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

    private String obtenerCargoSeleccionado() {
        Object selectedItem = spCargoTrabajador.getSelectedItem();
        if (selectedItem == null) {
            return "";
        }

        String cargo = selectedItem.toString().trim();
        if ("Seleccione un cargo".equalsIgnoreCase(cargo)) {
            return "";
        }

        return cargo;
    }
}