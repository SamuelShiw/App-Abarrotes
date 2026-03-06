package com.tienda.abarrotes.ui.reponedor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tienda.abarrotes.R;

public class ReponedorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponedor);

        if (savedInstanceState == null) {
            mostrarFragment(new ReposicionFragment());
        }
    }

    private void mostrarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerReponedor, fragment)
                .commit();
    }
}