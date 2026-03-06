package com.tienda.abarrotes.ui.cajero;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class AperturaCajaFragment extends Fragment {

    private EditText etMontoInicial;

    private CajaViewModel cajaViewModel;
    private SessionManager sessionManager;

    public AperturaCajaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apertura_caja, container, false);

        etMontoInicial = view.findViewById(R.id.etMontoInicialCaja);

        cajaViewModel = new ViewModelProvider(this).get(CajaViewModel.class);
        sessionManager = new SessionManager(requireContext());

        view.findViewById(R.id.btnAbrirCaja).setOnClickListener(v -> abrirCaja());

        return view;
    }

    private void abrirCaja() {

        String montoTexto = etMontoInicial.getText().toString();

        if (TextUtils.isEmpty(montoTexto)) {
            Toast.makeText(getContext(),"Ingrese monto inicial",Toast.LENGTH_SHORT).show();
            return;
        }

        int usuarioId = sessionManager.getUsuarioId();

        if (cajaViewModel.existeCajaAbierta(usuarioId)) {
            Toast.makeText(getContext(),"Ya tiene una caja abierta",Toast.LENGTH_SHORT).show();
            return;
        }

        Caja caja = new Caja();
        caja.setUsuarioId(usuarioId);
        caja.setMontoInicial(Double.parseDouble(montoTexto));
        caja.setFechaApertura(
                new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                        .format(new Date())
        );

        long resultado = cajaViewModel.abrirCaja(caja);

        if (resultado > 0) {
            Toast.makeText(getContext(),"Caja abierta correctamente",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),"Error al abrir caja",Toast.LENGTH_SHORT).show();
        }
    }
}