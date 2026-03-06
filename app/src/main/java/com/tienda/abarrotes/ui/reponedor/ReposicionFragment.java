package com.tienda.abarrotes.ui.reponedor;

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
import com.tienda.abarrotes.viewmodel.ReponedorViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReposicionFragment extends Fragment {

    private Spinner spProductoReposicion;
    private TextView tvInfoProductoReposicion;
    private EditText etCantidadReposicion;
    private Button btnRegistrarReposicion;

    private ReponedorViewModel reponedorViewModel;
    private SessionManager sessionManager;
    private List<Producto> listaProductos;

    public ReposicionFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reposicion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spProductoReposicion = view.findViewById(R.id.spProductoReposicion);
        tvInfoProductoReposicion = view.findViewById(R.id.tvInfoProductoReposicion);
        etCantidadReposicion = view.findViewById(R.id.etCantidadReposicion);
        btnRegistrarReposicion = view.findViewById(R.id.btnRegistrarReposicion);

        reponedorViewModel = new ViewModelProvider(this).get(ReponedorViewModel.class);
        sessionManager = new SessionManager(requireContext());

        cargarProductos();
        setListeners();
    }

    private void setListeners() {
        spProductoReposicion.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                mostrarInfoProducto();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });

        btnRegistrarReposicion.setOnClickListener(v -> registrarReposicion());
    }

    private void cargarProductos() {
        listaProductos = reponedorViewModel.listarProductosActivos();

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
        spProductoReposicion.setAdapter(adapter);

        mostrarInfoProducto();
    }

    private void mostrarInfoProducto() {
        if (listaProductos == null || listaProductos.isEmpty()) {
            tvInfoProductoReposicion.setText("No hay productos disponibles");
            return;
        }

        int posicion = spProductoReposicion.getSelectedItemPosition();
        if (posicion < 0 || posicion >= listaProductos.size()) {
            tvInfoProductoReposicion.setText("");
            return;
        }

        Producto producto = listaProductos.get(posicion);
        tvInfoProductoReposicion.setText(
                "Almacén: " + producto.getStockAlmacen() +
                        " | Estante: " + producto.getStockEstante()
        );
    }

    private void registrarReposicion() {
        if (listaProductos == null || listaProductos.isEmpty()) {
            Toast.makeText(requireContext(), "No hay productos disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        String cantidadTexto = etCantidadReposicion.getText().toString().trim();

        String validacion = reponedorViewModel.validarCantidad(cantidadTexto);
        if (validacion != null) {
            Toast.makeText(requireContext(), validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        int posicion = spProductoReposicion.getSelectedItemPosition();
        if (posicion < 0 || posicion >= listaProductos.size()) {
            Toast.makeText(requireContext(), "Seleccione un producto válido", Toast.LENGTH_SHORT).show();
            return;
        }

        int usuarioId = sessionManager.getUsuarioId();
        int productoId = listaProductos.get(posicion).getId();
        int cantidad = Integer.parseInt(cantidadTexto);
        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        long resultado = reponedorViewModel.registrarReposicion(usuarioId, productoId, cantidad, fecha);

        if (resultado > 0) {
            Toast.makeText(requireContext(), "Reposición registrada correctamente", Toast.LENGTH_SHORT).show();
            etCantidadReposicion.setText("");
            cargarProductos();
        } else if (resultado == -3) {
            Toast.makeText(requireContext(), "No hay suficiente stock en almacén", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "No se pudo registrar la reposición", Toast.LENGTH_SHORT).show();
        }
    }
}