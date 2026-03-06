package com.tienda.abarrotes.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.interfaces.OnProductoClickListener;
import com.tienda.abarrotes.ui.auth.LoginActivity;
import com.tienda.abarrotes.ui.common.utils.SessionManager;

public class AdminActivity extends AppCompatActivity implements OnProductoClickListener {

    private TextView tvNombreAdmin;
    private TextView tvRolAdmin;

    private Button btnModuloTrabajadores;
    private Button btnModuloUsuarios;
    private Button btnModuloProductos;
    private Button btnModuloSecciones;
    private Button btnModuloEstantes;
    private Button btnModuloInventario;
    private Button btnModuloConsultaCaja;
    private Button btnModuloConsultaReposicion;
    private Button btnCerrarSesionAdmin;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initObjects();
        initViews();
        cargarDatosSesion();
        setListeners();

        if (savedInstanceState == null) {
            mostrarFragment(new TrabajadoresFragment());
        }
    }

    private void initObjects() {
        sessionManager = new SessionManager(this);
    }

    private void initViews() {
        tvNombreAdmin = findViewById(R.id.tvNombreAdmin);
        tvRolAdmin = findViewById(R.id.tvRolAdmin);

        btnModuloTrabajadores = findViewById(R.id.btnModuloTrabajadores);
        btnModuloUsuarios = findViewById(R.id.btnModuloUsuarios);
        btnModuloProductos = findViewById(R.id.btnModuloProductos);
        btnModuloSecciones = findViewById(R.id.btnModuloSecciones);
        btnModuloEstantes = findViewById(R.id.btnModuloEstantes);
        btnModuloInventario = findViewById(R.id.btnModuloInventario);
        btnModuloConsultaCaja = findViewById(R.id.btnModuloConsultaCaja);
        btnModuloConsultaReposicion = findViewById(R.id.btnModuloConsultaReposicion);
        btnCerrarSesionAdmin = findViewById(R.id.btnCerrarSesionAdmin);
    }

    private void cargarDatosSesion() {
        tvNombreAdmin.setText("Administrador: " + sessionManager.getNombreCompleto());
        tvRolAdmin.setText("Rol: " + sessionManager.getRolNombre());
    }

    private void setListeners() {
        btnModuloTrabajadores.setOnClickListener(v ->
                mostrarFragment(new TrabajadoresFragment()));

        btnModuloUsuarios.setOnClickListener(v ->
                mostrarFragment(new UsuariosAdminFragment()));

        btnModuloProductos.setOnClickListener(v ->
                mostrarFragment(new ProductosFragment()));

        btnModuloSecciones.setOnClickListener(v ->
                mostrarFragment(new SeccionesFragment()));

        btnModuloEstantes.setOnClickListener(v ->
                mostrarFragment(new EstantesFragment()));

        btnModuloInventario.setOnClickListener(v ->
                mostrarFragment(new InventarioFragment()));

        btnModuloConsultaCaja.setOnClickListener(v ->
                mostrarMensajeModulo("Consulta de Caja"));

        btnModuloConsultaReposicion.setOnClickListener(v ->
                mostrarMensajeModulo("Consulta de Reposición"));

        btnCerrarSesionAdmin.setOnClickListener(v -> cerrarSesion());
    }

    private void mostrarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerAdmin, fragment)
                .commit();
    }

    private void mostrarMensajeModulo(String nombreModulo) {
        Toast.makeText(this, "Módulo " + nombreModulo + " en construcción", Toast.LENGTH_SHORT).show();
    }

    private void cerrarSesion() {
        sessionManager.cerrarSesion();

        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }

    @Override
    public void onProductoClick(int productoId) {
        Intent intent = new Intent(this, EditarProductoActivity.class);
        intent.putExtra("producto_id", productoId);
        startActivity(intent);
    }
}