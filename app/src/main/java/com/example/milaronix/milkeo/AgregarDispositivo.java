package com.example.milaronix.milkeo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by milaronix on 31/05/15.
 */
public class AgregarDispositivo extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_dispositivo);

        ImageView foto = (ImageView) findViewById(R.id.imagen_nueva);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Le hizo click a la imagen",Toast.LENGTH_SHORT).show();
                Intent foto = new Intent(Intent.ACTION_PICK);
                foto.setType("image/*");
                startActivityForResult(foto, 100);
            }
        });
    }
}
