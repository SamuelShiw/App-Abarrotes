package com.tienda.abarrotes.ui.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Estante;
import com.tienda.abarrotes.viewmodel.EstanteViewModel;

public class RegistrarEstanteActivity extends AppCompatActivity {

    private EditText etSeccionIdEstante;
    private EditText etCodigoEstante;
    private EditText etDescripcionEstante;
    private Button btnGuardarEstante;

    private EstanteViewModel estanteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_estante);

        estanteViewModel = new ViewModelProvider(this).get(EstanteViewModel.class);

        initViews();
        setListeners();
    }

    private void initViews() {
        etSeccionIdEstante = findViewById(R.id.etSeccionIdEstante);
        etCodigoEstante = findViewById(R.id.etCodigoEstante);
        etDescripcionEstante = findViewById(R.id.etDescripcionEstante);
        btnGuardarEstante = findViewById(R.id.btnGuardarEstante);
    }

    private void setListeners() {
        btnGuardarEstante.setOnClickListener(v -> guardarEstante());
    }

    private void guardarEstante() {
        String seccionIdTexto = etSeccionIdEstante.getText().toString().trim();
        String codigo = etCodigoEstante.getText().toString().trim();
        String descripcion = etDescripcionEstante.getText().toString().trim();

        String validacion = estanteViewModel.validarCampos(seccionIdTexto, codigo);
        if (validacion != null) {
            Toast.makeText(this, validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        int seccionId = Integer.parseInt(seccionIdTexto);

        if (!estanteViewModel.existeSeccion(seccionId)) {
            Toast.makeText(this, "No existe la sección indicada", Toast.LENGTH_SHORT).show();
            return;
        }

        if (estanteViewModel.existeCodigo(codigo)) {
            Toast.makeText(this, "El código del estante ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        Estante estante = new Estante();
        estante.setSeccionId(seccionId);
        estante.setCodigo(codigo);
        estante.setDescripcion(descripcion);
        estante.setEstado(1);

        long resultado = estanteViewModel.insertarEstante(estante);

        if (resultado > 0) {
            Toast.makeText(this, "Estante registrado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No se pudo registrar el estante", Toast.LENGTH_SHORT).show();
        }
    }
}