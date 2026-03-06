package com.tienda.abarrotes.ui.common.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.model.UsuarioAdminListado;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<UsuarioAdminListado> listaUsuarios;

    public UsuarioAdapter() {
        this.listaUsuarios = new ArrayList<>();
    }

    public void setListaUsuarios(List<UsuarioAdminListado> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        UsuarioAdminListado item = listaUsuarios.get(position);

        holder.tvNombreCompleto.setText(item.getNombreCompleto());
        holder.tvDni.setText("DNI: " + item.getDni());
        holder.tvUsername.setText("Usuario: " + item.getUsername());
        holder.tvRol.setText("Rol: " + item.getRol());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios != null ? listaUsuarios.size() : 0;
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvNombreCompleto;
        private final TextView tvDni;
        private final TextView tvUsername;
        private final TextView tvRol;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);
            tvDni = itemView.findViewById(R.id.tvDni);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvRol = itemView.findViewById(R.id.tvRol);
        }
    }
}