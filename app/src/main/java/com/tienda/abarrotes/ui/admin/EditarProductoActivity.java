package com.tienda.abarrotes.ui.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Producto;
import com.tienda.abarrotes.viewmodel.ProductoViewModel;

public class EditarProductoActivity extends AppCompatActivity {

    private EditText etCodigoEditarProducto;
    private EditText etNombreEditarProducto;
    private EditText etDescripcionEditarProducto;
    private EditText etPrecioEditarProducto;
    private EditText etStockAlmacenEditarProducto;
    private EditText etStockEstanteEditarProducto;
    private EditText etFechaVencimientoEditarProducto;
    private Button btnActualizarProducto;
    private Button btnDesactivarProducto;

    private ProductoViewModel productoViewModel;
    private Producto productoActual;
    private int productoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);

        initViews();

        productoId = getIntent().getIntExtra("producto_id", -1);
        if (productoId <= 0) {
            Toast.makeText(this, "Producto inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarProducto();
        setListeners();
    }

    private void initViews() {
        etCodigoEditarProducto = findViewById(R.id.etCodigoEditarProducto);
        etNombreEditarProducto = findViewById(R.id.etNombreEditarProducto);
        etDescripcionEditarProducto = findViewById(R.id.etDescripcionEditarProducto);
        etPrecioEditarProducto = findViewById(R.id.etPrecioEditarProducto);
        etStockAlmacenEditarProducto = findViewById(R.id.etStockAlmacenEditarProducto);
        etStockEstanteEditarProducto = findViewById(R.id.etStockEstanteEditarProducto);
        etFechaVencimientoEditarProducto = findViewById(R.id.etFechaVencimientoEditarProducto);
        btnActualizarProducto = findViewById(R.id.btnActualizarProducto);
        btnDesactivarProducto = findViewById(R.id.btnDesactivarProducto);
    }

    private void cargarProducto() {
        productoActual = productoViewModel.obtenerProductoPorId(productoId);

        if (productoActual == null) {
            Toast.makeText(this, "No se encontró el producto", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etCodigoEditarProducto.setText(productoActual.getCodigo());
        etNombreEditarProducto.setText(productoActual.getNombre());
        etDescripcionEditarProducto.setText(productoActual.getDescripcion());
        etPrecioEditarProducto.setText(String.valueOf(productoActual.getPrecio()));
        etStockAlmacenEditarProducto.setText(String.valueOf(productoActual.getStockAlmacen()));
        etStockEstanteEditarProducto.setText(String.valueOf(productoActual.getStockEstante()));
        etFechaVencimientoEditarProducto.setText(productoActual.getFechaVencimiento());
    }

    private void setListeners() {
        btnActualizarProducto.setOnClickListener(v -> actualizarProducto());
        btnDesactivarProducto.setOnClickListener(v -> desactivarProducto());
    }

    private void actualizarProducto() {
        String codigo = etCodigoEditarProducto.getText().toString().trim();
        String nombre = etNombreEditarProducto.getText().toString().trim();
        String descripcion = etDescripcionEditarProducto.getText().toString().trim();
        String precioTexto = etPrecioEditarProducto.getText().toString().trim();
        String stockAlmacenTexto = etStockAlmacenEditarProducto.getText().toString().trim();
        String stockEstanteTexto = etStockEstanteEditarProducto.getText().toString().trim();
        String fechaVencimiento = etFechaVencimientoEditarProducto.getText().toString().trim();

        String validacion = productoViewModel.validarCampos(
                codigo, nombre, precioTexto, stockAlmacenTexto, stockEstanteTexto
        );

        if (validacion != null) {
            Toast.makeText(this, validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        productoActual.setCodigo(codigo);
        productoActual.setNombre(nombre);
        productoActual.setDescripcion(descripcion);
        productoActual.setPrecio(Double.parseDouble(precioTexto));
        productoActual.setStockAlmacen(Integer.parseInt(stockAlmacenTexto));
        productoActual.setStockEstante(Integer.parseInt(stockEstanteTexto));
        productoActual.setFechaVencimiento(fechaVencimiento);

        boolean actualizado = productoViewModel.actualizarProducto(productoActual);

        if (actualizado) {
            Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No se pudo actualizar el producto", Toast.LENGTH_SHORT).show();
        }
    }

    private void desactivarProducto() {
        boolean desactivado = productoViewModel.desactivarProducto(productoId);

        if (desactivado) {
            Toast.makeText(this, "Producto desactivado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No se pudo desactivar el producto", Toast.LENGTH_SHORT).show();
        }
    }
}