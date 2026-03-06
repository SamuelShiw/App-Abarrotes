package com.tienda.abarrotes.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.data.db.DatabaseSeeder;
import com.tienda.abarrotes.ui.auth.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        inicializarBaseDatos();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }

    private void inicializarBaseDatos() {
        DatabaseSeeder seeder = new DatabaseSeeder(this);
        seeder.seedInitialData();
    }
}