package com.tienda.abarrotes.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Trabajador;

import java.util.ArrayList;
import java.util.List;

public class TrabajadorAdapter extends RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder> {

    private List<Trabajador> listaTrabajadores;

    public TrabajadorAdapter() {
        this.listaTrabajadores = new ArrayList<>();
    }

    public void setListaTrabajadores(List<Trabajador> listaTrabajadores) {
        this.listaTrabajadores = listaTrabajadores;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrabajadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trabajador, parent, false);
        return new TrabajadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrabajadorViewHolder holder, int position) {
        Trabajador trabajador = listaTrabajadores.get(position);

        holder.tvNombreCompletoTrabajador.setText(trabajador.getNombreCompleto());
        holder.tvDniTrabajador.setText("DNI: " + trabajador.getDni());
        holder.tvCargoTrabajador.setText("Cargo: " + trabajador.getCargo());
        holder.tvTelefonoTrabajador.setText("Teléfono: " +
                (trabajador.getTelefono() == null ? "" : trabajador.getTelefono()));
    }

    @Override
    public int getItemCount() {
        return listaTrabajadores != null ? listaTrabajadores.size() : 0;
    }

    static class TrabajadorViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvNombreCompletoTrabajador;
        private final TextView tvDniTrabajador;
        private final TextView tvCargoTrabajador;
        private final TextView tvTelefonoTrabajador;

        public TrabajadorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCompletoTrabajador = itemView.findViewById(R.id.tvNombreCompletoTrabajador);
            tvDniTrabajador = itemView.findViewById(R.id.tvDniTrabajador);
            tvCargoTrabajador = itemView.findViewById(R.id.tvCargoTrabajador);
            tvTelefonoTrabajador = itemView.findViewById(R.id.tvTelefonoTrabajador);
        }
    }
}