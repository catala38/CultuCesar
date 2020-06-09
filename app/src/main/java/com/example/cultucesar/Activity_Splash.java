package com.example.cultucesar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.cultucesar.Interfaces.MainActivity;

public class Activity_Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent inten = new Intent(Activity_Splash.this, seleccionar_destino.class);
                startActivity(inten);
                finish();
            }
        },2000);
    }
}
