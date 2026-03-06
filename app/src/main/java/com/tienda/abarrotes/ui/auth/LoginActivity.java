package com.tienda.abarrotes.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Trabajador;
import com.tienda.abarrotes.data.model.Usuario;
import com.tienda.abarrotes.ui.common.utils.RoleHelper;
import com.tienda.abarrotes.ui.common.utils.SessionManager;
import com.tienda.abarrotes.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    private LoginViewModel loginViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initObjects();
        initViews();
        verificarSesionActiva();
        setListeners();
    }

    private void initObjects() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sessionManager = new SessionManager(this);
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void verificarSesionActiva() {
        if (sessionManager.isLoggedIn()) {
            Intent intent = RoleHelper.getHomeIntentByRole(this, sessionManager.getRolNombre());
            if (intent != null) {
                startActivity(intent);
                finish();
            }
        }
    }

    private void setListeners() {
        btnLogin.setOnClickListener(v -> intentarLogin());
    }

    private void intentarLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        String validacion = loginViewModel.validarCampos(username, password);
        if (validacion != null) {
            Toast.makeText(this, validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = loginViewModel.login(username, password);

        if (usuario == null) {
            Toast.makeText(this, "Credenciales inválidas o usuario inactivo", Toast.LENGTH_SHORT).show();
            return;
        }

        Trabajador trabajador = loginViewModel.obtenerTrabajadorPorId(usuario.getTrabajadorId());
        if (trabajador == null) {
            Toast.makeText(this, "No se encontró el trabajador asociado", Toast.LENGTH_SHORT).show();
            return;
        }

        String rolNombre = loginViewModel.obtenerNombreRolPorId(usuario.getRolId());
        if (rolNombre.isEmpty()) {
            Toast.makeText(this, "No se pudo determinar el rol del usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        sessionManager.guardarSesion(
                usuario.getId(),
                usuario.getTrabajadorId(),
                usuario.getUsername(),
                usuario.getRolId(),
                rolNombre,
                trabajador.getNombreCompleto()
        );

        Intent intent = RoleHelper.getHomeIntentByRole(this, rolNombre);
        if (intent != null) {
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Rol no reconocido", Toast.LENGTH_SHORT).show();
        }
    }
}