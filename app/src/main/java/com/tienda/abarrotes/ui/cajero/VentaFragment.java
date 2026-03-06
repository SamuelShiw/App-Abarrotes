package com.tienda.abarrotes.ui.cajero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Producto;
import com.tienda.abarrotes.ui.common.utils.SessionManager;
import com.tienda.abarrotes.viewmodel.VentaViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VentaFragment extends Fragment {

    private Spinner spProductoVenta;
    private Spinner spTipoComprobanteVenta;
    private EditText etCantidadVenta;
    private TextView tvInfoProductoVenta;
    private TextView tvCarritoVenta;
    private TextView tvTotalVenta;
    private Button btnAgregarProductoVenta;
    private Button btnRegistrarVenta;
    private Button btnLimpiarCarritoVenta;

    private VentaViewModel ventaViewModel;
    private SessionManager sessionManager;
    private List<Producto> listaProductos;

    private final LinkedHashMap<Integer, Integer> carritoCantidades = new LinkedHashMap<>();
    private final LinkedHashMap<Integer, Producto> carritoProductos = new LinkedHashMap<>();

    public VentaFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spProductoVenta = view.findViewById(R.id.spProductoVenta);
        spTipoComprobanteVenta = view.findViewById(R.id.spTipoComprobanteVenta);
        etCantidadVenta = view.findViewById(R.id.etCantidadVenta);
        tvInfoProductoVenta = view.findViewById(R.id.tvInfoProductoVenta);
        tvCarritoVenta = view.findViewById(R.id.tvCarritoVenta);
        tvTotalVenta = view.findViewById(R.id.tvTotalVenta);
        btnAgregarProductoVenta = view.findViewById(R.id.btnAgregarProductoVenta);
        btnRegistrarVenta = view.findViewById(R.id.btnRegistrarVenta);
        btnLimpiarCarritoVenta = view.findViewById(R.id.btnLimpiarCarritoVenta);

        ventaViewModel = new ViewModelProvider(this).get(VentaViewModel.class);
        sessionManager = new SessionManager(requireContext());

        cargarProductos();
        cargarTiposComprobante();
        setListeners();
        actualizarCarritoUI();
    }

    private void setListeners() {
        spProductoVenta.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                mostrarInfoProducto();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        btnAgregarProductoVenta.setOnClickListener(v -> agregarProductoAlCarrito());
        btnRegistrarVenta.setOnClickListener(v -> registrarVenta());
        btnLimpiarCarritoVenta.setOnClickListener(v -> limpiarCarrito());
    }

    private void cargarProductos() {
        listaProductos = ventaViewModel.listarProductosActivos();

        List<String> items = new ArrayList<>();
        for (Producto producto : listaProductos) {
            items.add(producto.getId() + " - " + producto.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProductoVenta.setAdapter(adapter);

        mostrarInfoProducto();
    }

    private void cargarTiposComprobante() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                ventaViewModel.obtenerTiposComprobante()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoComprobanteVenta.setAdapter(adapter);
    }

    private void mostrarInfoProducto() {
        if (listaProductos == null || listaProductos.isEmpty()) {
            tvInfoProductoVenta.setText("No hay productos disponibles");
            return;
        }

        int posicion = spProductoVenta.getSelectedItemPosition();
        if (posicion < 0 || posicion >= listaProductos.size()) {
            tvInfoProductoVenta.setText("");
            return;
        }

        Producto producto = listaProductos.get(posicion);
        int cantidadEnCarrito = carritoCantidades.containsKey(producto.getId())
                ? carritoCantidades.get(producto.getId())
                : 0;

        tvInfoProductoVenta.setText(
                "Precio: S/ " + producto.getPrecio() +
                        " | Stock estante: " + producto.getStockEstante() +
                        " | En carrito: " + cantidadEnCarrito
        );
    }

    private void agregarProductoAlCarrito() {
        if (listaProductos == null || listaProductos.isEmpty()) {
            Toast.makeText(requireContext(), "No hay productos disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        String cantidadTexto = etCantidadVenta.getText().toString().trim();
        String tipoComprobante = spTipoComprobanteVenta.getSelectedItem() != null
                ? spTipoComprobanteVenta.getSelectedItem().toString()
                : "";

        String validacion = ventaViewModel.validarCampos(cantidadTexto, tipoComprobante);
        if (validacion != null) {
            Toast.makeText(requireContext(), validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        int posicion = spProductoVenta.getSelectedItemPosition();
        if (posicion < 0 || posicion >= listaProductos.size()) {
            Toast.makeText(requireContext(), "Seleccione un producto válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Producto producto = listaProductos.get(posicion);
        int cantidadNueva = Integer.parseInt(cantidadTexto);
        int cantidadActualEnCarrito = carritoCantidades.containsKey(producto.getId())
                ? carritoCantidades.get(producto.getId())
                : 0;

        int cantidadTotal = cantidadActualEnCarrito + cantidadNueva;

        if (cantidadTotal > producto.getStockEstante()) {
            Toast.makeText(requireContext(), "La cantidad total supera el stock disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        carritoProductos.put(producto.getId(), producto);
        carritoCantidades.put(producto.getId(), cantidadTotal);

        etCantidadVenta.setText("");
        actualizarCarritoUI();
        mostrarInfoProducto();

        Toast.makeText(requireContext(), "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
    }

    private void actualizarCarritoUI() {
        if (carritoProductos.isEmpty()) {
            tvCarritoVenta.setText("Carrito vacío");
            tvTotalVenta.setText("Total: S/ 0.0");
            return;
        }

        StringBuilder sb = new StringBuilder();
        double total = 0.0;

        for (Map.Entry<Integer, Producto> entry : carritoProductos.entrySet()) {
            int productoId = entry.getKey();
            Producto producto = entry.getValue();
            int cantidad = carritoCantidades.get(productoId);
            double subtotal = producto.getPrecio() * cantidad;

            sb.append(producto.getNombre())
                    .append(" | Cant: ")
                    .append(cantidad)
                    .append(" | Subtotal: S/ ")
                    .append(subtotal)
                    .append("\n");

            total += subtotal;
        }

        tvCarritoVenta.setText(sb.toString().trim());
        tvTotalVenta.setText("Total: S/ " + total);
    }

    private void limpiarCarrito() {
        carritoProductos.clear();
        carritoCantidades.clear();
        actualizarCarritoUI();
        mostrarInfoProducto();
        Toast.makeText(requireContext(), "Carrito limpiado", Toast.LENGTH_SHORT).show();
    }

    private void registrarVenta() {
        int usuarioId = sessionManager.getUsuarioId();

        if (!ventaViewModel.existeCajaAbierta(usuarioId)) {
            Toast.makeText(requireContext(), "Primero debe abrir caja", Toast.LENGTH_SHORT).show();
            return;
        }

        if (carritoProductos.isEmpty()) {
            Toast.makeText(requireContext(), "Agregue al menos un producto al carrito", Toast.LENGTH_SHORT).show();
            return;
        }

        String tipoComprobante = spTipoComprobanteVenta.getSelectedItem() != null
                ? spTipoComprobanteVenta.getSelectedItem().toString()
                : "";

        if (tipoComprobante.isEmpty()) {
            Toast.makeText(requireContext(), "Seleccione tipo de comprobante", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Integer> productoIds = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : carritoCantidades.entrySet()) {
            productoIds.add(entry.getKey());
            cantidades.add(entry.getValue());
        }

        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        long resultado = ventaViewModel.registrarVentaMultiple(
                usuarioId,
                productoIds,
                cantidades,
                fecha,
                tipoComprobante
        );

        if (resultado > 0) {
            Toast.makeText(requireContext(), "Venta registrada correctamente - " + tipoComprobante, Toast.LENGTH_SHORT).show();
            limpiarCarrito();
            cargarProductos();
        } else if (resultado == -6) {
            Toast.makeText(requireContext(), "Algún producto no tiene stock suficiente", Toast.LENGTH_SHORT).show();
        } else if (resultado == -1) {
            Toast.makeText(requireContext(), "No hay caja abierta", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "No se pudo registrar la venta", Toast.LENGTH_SHORT).show();
        }
    }
}