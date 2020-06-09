package com.example.cultucesar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.cultucesar.Interfaces.MainActivity;

public class seleccionar_destino extends AppCompatActivity {

    private ImageButton botonValledupar,botonManaure;
    public static String destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_destino);

        botonValledupar = (ImageButton) findViewById(R.id.imageButtonValledupar);
        botonValledupar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destino = "Valledupar";
                Intent intent = new Intent(seleccionar_destino.this, MainActivity.class); //ELEGIMOS LA ACTIVITY QUE QUEREMOS EJECUTAR
                startActivity(intent);
                finish();
            }
        });

        botonManaure = (ImageButton) findViewById(R.id.imageButtonManaure);
        botonManaure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destino = "Manaure";
                Intent intent = new Intent(seleccionar_destino.this, MainActivity.class); //ELEGIMOS LA ACTIVITY QUE QUEREMOS EJECUTAR
                startActivity(intent);
                finish();
            }
        });
    }
}
