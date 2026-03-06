package com.tienda.abarrotes.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Estante;

import java.util.ArrayList;
import java.util.List;

public class EstanteAdapter extends RecyclerView.Adapter<EstanteAdapter.EstanteViewHolder> {

    private List<Estante> listaEstantes;

    public EstanteAdapter() {
        this.listaEstantes = new ArrayList<>();
    }

    public void setListaEstantes(List<Estante> listaEstantes) {
        this.listaEstantes = listaEstantes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EstanteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estante, parent, false);
        return new EstanteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstanteViewHolder holder, int position) {
        Estante estante = listaEstantes.get(position);
        holder.tvCodigoEstante.setText("Código: " + estante.getCodigo());
        holder.tvSeccionEstante.setText("Sección ID: " + estante.getSeccionId());
        holder.tvDescripcionEstante.setText("Descripción: " +
                (estante.getDescripcion() == null ? "" : estante.getDescripcion()));
    }

    @Override
    public int getItemCount() {
        return listaEstantes != null ? listaEstantes.size() : 0;
    }

    static class EstanteViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvCodigoEstante;
        private final TextView tvSeccionEstante;
        private final TextView tvDescripcionEstante;

        public EstanteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodigoEstante = itemView.findViewById(R.id.tvCodigoEstante);
            tvSeccionEstante = itemView.findViewById(R.id.tvSeccionEstante);
            tvDescripcionEstante = itemView.findViewById(R.id.tvDescripcionEstante);
        }
    }
}