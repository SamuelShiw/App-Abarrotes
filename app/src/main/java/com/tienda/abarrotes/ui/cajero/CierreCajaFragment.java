package com.tienda.abarrotes.ui.cajero;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Caja;
import com.tienda.abarrotes.ui.common.utils.SessionManager;
import com.tienda.abarrotes.viewmodel.CajaViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CierreCajaFragment extends Fragment {

    private TextView tvInfoCajaAbierta;
    private EditText etMontoFinalCaja;

    private CajaViewModel cajaViewModel;
    private SessionManager sessionManager;

    public CierreCajaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cierre_caja, container, false);

        tvInfoCajaAbierta = view.findViewById(R.id.tvInfoCajaAbierta);
        etMontoFinalCaja = view.findViewById(R.id.etMontoFinalCaja);

        cajaViewModel = new ViewModelProvider(this).get(CajaViewModel.class);
        sessionManager = new SessionManager(requireContext());

        view.findViewById(R.id.btnCerrarCaja).setOnClickListener(v -> cerrarCaja());

        cargarInfoCaja();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarInfoCaja();
    }

    private void cargarInfoCaja() {
        int usuarioId = sessionManager.getUsuarioId();
        Caja caja = cajaViewModel.obtenerCajaAbierta(usuarioId);

        if (caja == null) {
            tvInfoCajaAbierta.setText("No hay caja abierta");
        } else {
            tvInfoCajaAbierta.setText(
                    "Caja abierta\n" +
                            "Fecha apertura: " + caja.getFechaApertura() + "\n" +
                            "Monto inicial: S/ " + caja.getMontoInicial()
            );
        }
    }

    private void cerrarCaja() {
        int usuarioId = sessionManager.getUsuarioId();

        if (!cajaViewModel.existeCajaAbierta(usuarioId)) {
            Toast.makeText(getContext(), "No hay caja abierta para cerrar", Toast.LENGTH_SHORT).show();
            return;
        }

        String montoTexto = etMontoFinalCaja.getText().toString().trim();

        String validacion = cajaViewModel.validarMonto(montoTexto);
        if (validacion != null) {
            Toast.makeText(getContext(), validacion, Toast.LENGTH_SHORT).show();
            return;
        }

        double montoFinal = Double.parseDouble(montoTexto);
        String fechaCierre = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());

        boolean resultado = cajaViewModel.cerrarCaja(usuarioId, fechaCierre, montoFinal);

        if (resultado) {
            Toast.makeText(getContext(), "Caja cerrada correctamente", Toast.LENGTH_SHORT).show();
            etMontoFinalCaja.setText("");
            cargarInfoCaja();
        } else {
            Toast.makeText(getContext(), "No se pudo cerrar la caja", Toast.LENGTH_SHORT).show();
        }
    }
}