package com.tienda.abarrotes.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.Producto;
import com.tienda.abarrotes.interfaces.OnProductoClickListener;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> listaProductos;
    private OnProductoClickListener listener;

    public ProductoAdapter() {
        this.listaProductos = new ArrayList<>();
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        notifyDataSetChanged();
    }

    public void setOnProductoClickListener(OnProductoClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);

        holder.tvCodigoProducto.setText("Código: " + producto.getCodigo());
        holder.tvNombreProducto.setText(producto.getNombre());
        holder.tvPrecioProducto.setText("Precio: S/ " + producto.getPrecio());
        holder.tvStockProducto.setText("Almacén: " + producto.getStockAlmacen() +
                " | Estante: " + producto.getStockEstante());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductoClick(producto.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos != null ? listaProductos.size() : 0;
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvCodigoProducto;
        private final TextView tvNombreProducto;
        private final TextView tvPrecioProducto;
        private final TextView tvStockProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodigoProducto = itemView.findViewById(R.id.tvCodigoProducto);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecioProducto = itemView.findViewById(R.id.tvPrecioProducto);
            tvStockProducto = itemView.findViewById(R.id.tvStockProducto);
        }
    }
}