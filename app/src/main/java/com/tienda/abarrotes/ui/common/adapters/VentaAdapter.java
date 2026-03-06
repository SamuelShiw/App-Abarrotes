package com.tienda.abarrotes.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Venta;

import java.util.ArrayList;
import java.util.List;

public class VentaAdapter extends RecyclerView.Adapter<VentaAdapter.VentaViewHolder> {

    private List<Venta> listaVentas = new ArrayList<>();

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venta, parent, false);
        return new VentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VentaViewHolder holder, int position) {
        Venta venta = listaVentas.get(position);
        holder.tvVentaId.setText("Venta ID: " + venta.getId());
        holder.tvVentaFecha.setText("Fecha: " + venta.getFecha());
        holder.tvVentaTotal.setText("Total: S/ " + venta.getTotal());
    }

    @Override
    public int getItemCount() {
        return listaVentas != null ? listaVentas.size() : 0;
    }

    static class VentaViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvVentaId;
        private final TextView tvVentaFecha;
        private final TextView tvVentaTotal;

        public VentaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVentaId = itemView.findViewById(R.id.tvVentaId);
            tvVentaFecha = itemView.findViewById(R.id.tvVentaFecha);
            tvVentaTotal = itemView.findViewById(R.id.tvVentaTotal);
        }
    }
}