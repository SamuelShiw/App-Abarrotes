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
import com.tienda.abarrotes.data.model.Rol;
import com.tienda.abarrotes.data.model.Trabajador;
import com.tienda.abarrotes.data.model.Usuario;
import com.tienda.abarrotes.repository.TrabajadorRepository;
import com.tienda.abarrotes.viewmodel.UsuarioViewModel;

import java.util.List;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText etTrabajadorIdUsuario;
    private EditText etUsernameUsuario;
    private EditText etPasswordUsuario;
    private Spinner spRolUsuario;
    private Button btnGuardarUsuario;

    private UsuarioViewModel usuarioViewModel;
    private TrabajadorRepository trabajadorRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        trabajadorRepository = new TrabajadorRepository(this);

        initViews();
        cargarRoles();
        setListeners();
    }

    private void initViews() {
        etTrabajadorIdUsuario = findViewById(R.id.etTrabajadorIdUsuario);
        etUsernameUsuario = findViewById(R.id.etUsernameUsuario);
        etPasswordUsuario = findViewById(R.id.etPasswordUsuario);
        spRolUsuario = findViewById(R.id.spRolUsuario);
        btnGuardarUsuario = findViewById(R.id.btnGuardarUsuario);
    }

    private void cargarRoles() {
        List<String> roles = usuarioViewModel.obtenerRolesDisponiblesParaAdmin();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                roles
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRolUsuario.setAdapter(adapter);
    }

    private void setListeners() {
        btnGuardarUsuario.setOnClickListener(v -> guardarUsuario());
    }

    private void guardarUsuario() {
        String trabajadorIdTexto = etTrabajadorIdUsuario.getText().toString().trim();
        String username = etUsernameUsuario.getText().toString().trim();
        String password = etPasswordUsuario.getText().toString().trim();
        String nombreRol = spRolUsuario.getSelectedItem() != null
                ? spRolUsuario.getSelectedItem().toString()
                : "";

        int trabajadorId;
        try {
            trabajadorId = Integer.parseInt(trabajadorIdTexto);
        } catch (Exception e) {
            trabajadorId = -1;
        }

        String validacion = usuarioViewModel.validarCampos(trabajadorId, username, password, nombreRol);
        if (validacion != null) {
            Toast.makeText(this, validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        Trabajador trabajador = trabajadorRepository.obtenerTrabajadorPorId(trabajadorId);
        if (trabajador == null) {
            Toast.makeText(this, "No existe el trabajador indicado", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usuarioViewModel.existeUsuarioActivoPorTrabajador(trabajadorId)) {
            Toast.makeText(this, "El trabajador ya tiene una cuenta activa", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usuarioViewModel.existeUsername(username)) {
            Toast.makeText(this, "El username ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        Rol rol = usuarioViewModel.obtenerRolPorNombre(nombreRol);
        if (rol == null) {
            Toast.makeText(this, "No se encontró el rol seleccionado", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setTrabajadorId(trabajadorId);
        usuario.setRolId(rol.getId());
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setEstado(1);

        long resultado = usuarioViewModel.insertarUsuario(usuario);

        if (resultado > 0) {
            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
        }
    }
}