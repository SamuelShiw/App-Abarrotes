package com.tienda.abarrotes.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.MovimientoInventario;

import java.util.ArrayList;
import java.util.List;

public class MovimientoAdapter extends RecyclerView.Adapter<MovimientoAdapter.MovimientoViewHolder> {

    private List<MovimientoInventario> listaMovimientos;

    public MovimientoAdapter() {
        this.listaMovimientos = new ArrayList<>();
    }

    public void setListaMovimientos(List<MovimientoInventario> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovimientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movimiento, parent, false);
        return new MovimientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovimientoViewHolder holder, int position) {
        MovimientoInventario movimiento = listaMovimientos.get(position);

        holder.tvTipoMovimiento.setText("Tipo: " + movimiento.getTipoMovimiento());
        holder.tvCantidadMovimiento.setText("Cantidad: " + movimiento.getCantidad());
        holder.tvProductoMovimiento.setText("Producto ID: " + movimiento.getProductoId());
        holder.tvFechaMovimiento.setText("Fecha: " + movimiento.getFecha());
        holder.tvObservacionMovimiento.setText("Obs: " +
                (movimiento.getObservacion() == null ? "" : movimiento.getObservacion()));
    }

    @Override
    public int getItemCount() {
        return listaMovimientos != null ? listaMovimientos.size() : 0;
    }

    static class MovimientoViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTipoMovimiento;
        private final TextView tvCantidadMovimiento;
        private final TextView tvProductoMovimiento;
        private final TextView tvFechaMovimiento;
        private final TextView tvObservacionMovimiento;

        public MovimientoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipoMovimiento = itemView.findViewById(R.id.tvTipoMovimiento);
            tvCantidadMovimiento = itemView.findViewById(R.id.tvCantidadMovimiento);
            tvProductoMovimiento = itemView.findViewById(R.id.tvProductoMovimiento);
            tvFechaMovimiento = itemView.findViewById(R.id.tvFechaMovimiento);
            tvObservacionMovimiento = itemView.findViewById(R.id.tvObservacionMovimiento);
        }
    }
}