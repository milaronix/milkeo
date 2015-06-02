package com.example.milaronix.milkeo;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.milaronix.milkeo.fragments.Dispositivos;

import java.util.List;

/**
 * Created by milaronix on 31/05/15.
 */
public class AgregarDispositivo extends Activity{
    Dispositivo dispositivo = new Dispositivo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_dispositivo);

        ImageView la_foto = (ImageView) findViewById(R.id.imagen_nueva);
        la_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent escogeFoto = new Intent(Intent.ACTION_PICK);
                escogeFoto.setType("image/*");
                startActivityForResult(escogeFoto, 0);
            }
        });



        Button guardar = (Button) findViewById(R.id.b_guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nombre = (EditText) findViewById(R.id.ingreso_nombre);
                EditText pin = (EditText) findViewById(R.id.ingreso_pin);
                dispositivo.setNombre(nombre.getText().toString());
                dispositivo.setPin(pin.getText().toString());
                dispositivo.setEstado("0");
                dispositivo.setImg_estado(0);
                ControlBDDispositivos bd = new ControlBDDispositivos(getApplicationContext());
                bd.insertarDispositivo(dispositivo);
                Log.d("------->Paso del Query","<--------");
                Toast.makeText(getApplicationContext(),"Guardado",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView foto = (ImageView) findViewById(R.id.imagen_nueva);
        switch (requestCode){
            case 0:
                if (resultCode == RESULT_OK){
                    Uri imagenSeleccionada = data.getData();
                    foto.setImageURI(imagenSeleccionada);
                    dispositivo.setImagen(imagenSeleccionada.toString());
                }
                break;
        }
    }
}
