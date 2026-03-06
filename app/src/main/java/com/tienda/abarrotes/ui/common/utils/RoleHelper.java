package com.tienda.abarrotes.ui.common.utils;

import android.content.Context;
import android.content.Intent;

import com.tienda.abarrotes.ui.admin.AdminActivity;
import com.tienda.abarrotes.ui.cajero.CajeroActivity;
import com.tienda.abarrotes.ui.reponedor.ReponedorActivity;
import com.tienda.abarrotes.ui.superadmin.SuperAdminActivity;

public class RoleHelper {

    public static Intent getHomeIntentByRole(Context context, String rolNombre) {
        switch (rolNombre) {
            case "SUPERADMINISTRADOR":
                return new Intent(context, SuperAdminActivity.class);
            case "ADMINISTRADOR":
                return new Intent(context, AdminActivity.class);
            case "CAJERO":
                return new Intent(context, CajeroActivity.class);
            case "REPONEDOR":
                return new Intent(context, ReponedorActivity.class);
            default:
                return null;
        }
    }
}