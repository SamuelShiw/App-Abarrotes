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
import com.tienda.abarrotes.data.dao.EstanteDao;
import com.tienda.abarrotes.data.dao.SeccionDao;
import com.tienda.abarrotes.data.model.Estante;
import com.tienda.abarrotes.data.model.Producto;
import com.tienda.abarrotes.data.model.Seccion;
import com.tienda.abarrotes.viewmodel.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegistrarProductoActivity extends AppCompatActivity {

    private Spinner spSeccionProducto;
    private Spinner spEstanteProducto;
    private EditText etCodigoProducto;
    private EditText etNombreProducto;
    private EditText etDescripcionProducto;
    private EditText etPrecioProducto;
    private EditText etStockAlmacenProducto;
    private EditText etStockEstanteProducto;
    private EditText etFechaVencimientoProducto;
    private Button btnGuardarProducto;

    private ProductoViewModel productoViewModel;
    private SeccionDao seccionDao;
    private EstanteDao estanteDao;

    private List<Seccion> listaSecciones;
    private List<Estante> listaEstantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);

        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        seccionDao = new SeccionDao(this);
        estanteDao = new EstanteDao(this);

        initViews();
        cargarSecciones();
        setListeners();
    }

    private void initViews() {
        spSeccionProducto = findViewById(R.id.spSeccionProducto);
        spEstanteProducto = findViewById(R.id.spEstanteProducto);
        etCodigoProducto = findViewById(R.id.etCodigoProducto);
        etNombreProducto = findViewById(R.id.etNombreProducto);
        etDescripcionProducto = findViewById(R.id.etDescripcionProducto);
        etPrecioProducto = findViewById(R.id.etPrecioProducto);
        etStockAlmacenProducto = findViewById(R.id.etStockAlmacenProducto);
        etStockEstanteProducto = findViewById(R.id.etStockEstanteProducto);
        etFechaVencimientoProducto = findViewById(R.id.etFechaVencimientoProducto);
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto);
    }

    private void setListeners() {
        spSeccionProducto.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                cargarEstantesPorSeccion();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        btnGuardarProducto.setOnClickListener(v -> guardarProducto());
    }

    private void cargarSecciones() {
        listaSecciones = seccionDao.listarSeccionesActivas();

        List<String> items = new ArrayList<>();
        for (Seccion seccion : listaSecciones) {
            items.add(seccion.getId() + " - " + seccion.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSeccionProducto.setAdapter(adapter);

        if (!listaSecciones.isEmpty()) {
            cargarEstantesPorSeccion();
        }
    }

    private void cargarEstantesPorSeccion() {
        if (listaSecciones == null || listaSecciones.isEmpty()) {
            listaEstantes = new ArrayList<>();
            spEstanteProducto.setAdapter(null);
            return;
        }

        int posicionSeccion = spSeccionProducto.getSelectedItemPosition();
        if (posicionSeccion < 0 || posicionSeccion >= listaSecciones.size()) {
            return;
        }

        int seccionId = listaSecciones.get(posicionSeccion).getId();
        listaEstantes = estanteDao.listarEstantesActivosPorSeccion(seccionId);

        List<String> items = new ArrayList<>();
        for (Estante estante : listaEstantes) {
            items.add(estante.getId() + " - " + estante.getCodigo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstanteProducto.setAdapter(adapter);
    }

    private void guardarProducto() {
        String codigo = etCodigoProducto.getText().toString().trim();
        String nombre = etNombreProducto.getText().toString().trim();
        String descripcion = etDescripcionProducto.getText().toString().trim();
        String precioTexto = etPrecioProducto.getText().toString().trim();
        String stockAlmacenTexto = etStockAlmacenProducto.getText().toString().trim();
        String stockEstanteTexto = etStockEstanteProducto.getText().toString().trim();
        String fechaVencimiento = etFechaVencimientoProducto.getText().toString().trim();

        if (listaSecciones == null || listaSecciones.isEmpty()) {
            Toast.makeText(this, "Debe registrar al menos una sección", Toast.LENGTH_SHORT).show();
            return;
        }

        if (listaEstantes == null || listaEstantes.isEmpty()) {
            Toast.makeText(this, "Debe registrar al menos un estante para la sección elegida", Toast.LENGTH_SHORT).show();
            return;
        }

        String validacion = productoViewModel.validarCampos(
                codigo, nombre, precioTexto, stockAlmacenTexto, stockEstanteTexto
        );

        if (validacion != null) {
            Toast.makeText(this, validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        if (productoViewModel.existeCodigo(codigo)) {
            Toast.makeText(this, "El código ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        int posicionSeccion = spSeccionProducto.getSelectedItemPosition();
        int posicionEstante = spEstanteProducto.getSelectedItemPosition();

        if (posicionSeccion < 0 || posicionSeccion >= listaSecciones.size()) {
            Toast.makeText(this, "Seleccione una sección válida", Toast.LENGTH_SHORT).show();
            return;
        }

        if (posicionEstante < 0 || posicionEstante >= listaEstantes.size()) {
            Toast.makeText(this, "Seleccione un estante válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Producto producto = new Producto();
        producto.setSeccionId(listaSecciones.get(posicionSeccion).getId());
        producto.setEstanteId(listaEstantes.get(posicionEstante).getId());
        producto.setCodigo(codigo);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(Double.parseDouble(precioTexto));
        producto.setStockAlmacen(Integer.parseInt(stockAlmacenTexto));
        producto.setStockEstante(Integer.parseInt(stockEstanteTexto));
        producto.setFechaVencimiento(fechaVencimiento);
        producto.setEstado(1);

        long resultado = productoViewModel.insertarProducto(producto);

        if (resultado > 0) {
            Toast.makeText(this, "Producto registrado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No se pudo registrar el producto", Toast.LENGTH_SHORT).show();
        }
    }
}