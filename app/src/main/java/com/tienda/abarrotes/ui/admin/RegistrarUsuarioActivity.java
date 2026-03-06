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
import com.tienda.abarrotes.ui.common.utils.SessionManager;
import com.tienda.abarrotes.viewmodel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private Spinner spTrabajadorUsuario;
    private EditText etUsernameUsuario;
    private EditText etPasswordUsuario;
    private Spinner spRolUsuario;
    private Button btnGuardarUsuario;

    private UsuarioViewModel usuarioViewModel;
    private TrabajadorRepository trabajadorRepository;
    private SessionManager sessionManager;

    private final List<Trabajador> listaTrabajadores = new ArrayList<>();
    private final List<String> itemsTrabajadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        trabajadorRepository = new TrabajadorRepository(this);
        sessionManager = new SessionManager(this);

        initViews();
        cargarTrabajadores();
        cargarRoles();
        setListeners();
    }

    private void initViews() {
        spTrabajadorUsuario = findViewById(R.id.etTrabajadorIdUsuario);
        etUsernameUsuario = findViewById(R.id.etUsernameUsuario);
        etPasswordUsuario = findViewById(R.id.etPasswordUsuario);
        spRolUsuario = findViewById(R.id.spRolUsuario);
        btnGuardarUsuario = findViewById(R.id.btnGuardarUsuario);
    }

    private void cargarTrabajadores() {
        listaTrabajadores.clear();
        itemsTrabajadores.clear();

        itemsTrabajadores.add("Seleccione un trabajador");

        List<Trabajador> trabajadoresActivos = trabajadorRepository.listarTrabajadoresActivos();
        if (trabajadoresActivos != null) {
            for (Trabajador trabajador : trabajadoresActivos) {
                if (!usuarioViewModel.existeUsuarioActivoPorTrabajador(trabajador.getId())) {
                    listaTrabajadores.add(trabajador);

                    String item = trabajador.getId() + " - "
                            + trabajador.getNombres() + " "
                            + trabajador.getApellidos()
                            + " (" + trabajador.getCargo() + ")";

                    itemsTrabajadores.add(item);
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                itemsTrabajadores
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTrabajadorUsuario.setAdapter(adapter);
    }

    private void cargarRoles() {
        String rolLogueado = sessionManager.getRolNombre();
        List<String> roles = usuarioViewModel.obtenerRolesDisponiblesSegunSesion(rolLogueado);

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
        Trabajador trabajadorSeleccionado = obtenerTrabajadorSeleccionado();
        String username = etUsernameUsuario.getText().toString().trim();
        String password = etPasswordUsuario.getText().toString().trim();
        String nombreRol = obtenerRolSeleccionado();
        String rolLogueado = sessionManager.getRolNombre();

        int trabajadorId = trabajadorSeleccionado != null ? trabajadorSeleccionado.getId() : -1;

        String validacion = usuarioViewModel.validarCampos(
                trabajadorId,
                username,
                password,
                nombreRol,
                rolLogueado
        );

        if (validacion != null) {
            Toast.makeText(this, validacion, Toast.LENGTH_SHORT).show();
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

    private Trabajador obtenerTrabajadorSeleccionado() {
        int position = spTrabajadorUsuario.getSelectedItemPosition();

        if (position <= 0) {
            return null;
        }

        int indexReal = position - 1;
        if (indexReal < 0 || indexReal >= listaTrabajadores.size()) {
            return null;
        }

        return listaTrabajadores.get(indexReal);
    }

    private String obtenerRolSeleccionado() {
        Object selectedItem = spRolUsuario.getSelectedItem();
        if (selectedItem == null) {
            return "";
        }

        String rol = selectedItem.toString().trim();
        if ("Seleccione un rol".equalsIgnoreCase(rol)) {
            return "";
        }

        return rol;
    }
}