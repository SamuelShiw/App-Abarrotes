package com.tienda.abarrotes.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Seccion;

import java.util.ArrayList;
import java.util.List;

public class SeccionAdapter extends RecyclerView.Adapter<SeccionAdapter.SeccionViewHolder> {

    private List<Seccion> listaSecciones;

    public SeccionAdapter() {
        this.listaSecciones = new ArrayList<>();
    }

    public void setListaSecciones(List<Seccion> listaSecciones) {
        this.listaSecciones = listaSecciones;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeccionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seccion, parent, false);
        return new SeccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeccionViewHolder holder, int position) {
        Seccion seccion = listaSecciones.get(position);
        holder.tvNombreSeccion.setText(seccion.getNombre());
        holder.tvDescripcionSeccion.setText("Descripción: " +
                (seccion.getDescripcion() == null ? "" : seccion.getDescripcion()));
    }

    @Override
    public int getItemCount() {
        return listaSecciones != null ? listaSecciones.size() : 0;
    }

    static class SeccionViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvNombreSeccion;
        private final TextView tvDescripcionSeccion;

        public SeccionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreSeccion = itemView.findViewById(R.id.tvNombreSeccion);
            tvDescripcionSeccion = itemView.findViewById(R.id.tvDescripcionSeccion);
        }
    }
}