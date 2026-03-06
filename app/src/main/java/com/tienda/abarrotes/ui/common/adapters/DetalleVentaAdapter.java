package com.tienda.abarrotes.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.DetalleVenta;

import java.util.ArrayList;
import java.util.List;

public class DetalleVentaAdapter extends RecyclerView.Adapter<DetalleVentaAdapter.DetalleVentaViewHolder> {

    private List<DetalleVenta> listaDetalles = new ArrayList<>();

    public void setListaDetalles(List<DetalleVenta> listaDetalles) {
        this.listaDetalles = listaDetalles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetalleVentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle_venta, parent, false);
        return new DetalleVentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleVentaViewHolder holder, int position) {
        DetalleVenta item = listaDetalles.get(position);
        holder.tvDetalleProductoId.setText("Producto ID: " + item.getProductoId());
        holder.tvDetalleCantidad.setText("Cantidad: " + item.getCantidad());
        holder.tvDetallePrecio.setText("P. Unitario: S/ " + item.getPrecioUnitario());
        holder.tvDetalleSubtotal.setText("Subtotal: S/ " + item.getSubtotal());
    }

    @Override
    public int getItemCount() {
        return listaDetalles != null ? listaDetalles.size() : 0;
    }

    static class DetalleVentaViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvDetalleProductoId;
        private final TextView tvDetalleCantidad;
        private final TextView tvDetallePrecio;
        private final TextView tvDetalleSubtotal;

        public DetalleVentaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDetalleProductoId = itemView.findViewById(R.id.tvDetalleProductoId);
            tvDetalleCantidad = itemView.findViewById(R.id.tvDetalleCantidad);
            tvDetallePrecio = itemView.findViewById(R.id.tvDetallePrecio);
            tvDetalleSubtotal = itemView.findViewById(R.id.tvDetalleSubtotal);
        }
    }
}