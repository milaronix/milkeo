package com.example.milaronix.milkeo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by milaronix on 31/05/15.
 */
public class DetalleDispositivo extends Activity{
    View rootView = null;
    String nombre = null;
    String pin = null;
    String estado = null;
    int img_estado = 0;
    int imagen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_dispositivo);


        Bundle extras = getIntent().getExtras();
        nombre = extras.getString("nombre");
        pin = extras.getString("pin");
        estado = extras.getString("estado");
        img_estado = extras.getInt("img_estado");
        imagen = extras.getInt("imagen");

        Log.d("*********PRUEBA string", nombre);

        ImageView imagen_i = (ImageView) findViewById(R.id.imagen);
        imagen_i.setImageResource(imagen);

        TextView el_nombre = (TextView) findViewById(R.id.el_nombre);
        TextView el_pin = (TextView) findViewById(R.id.el_pin);
        el_nombre.setText(nombre);
        el_pin.setText(pin);

        final EditText miprueba = (EditText) findViewById(R.id.miprueba);
        final String recibe = "";

        final ImageButton boton = (ImageButton) findViewById(R.id.b_estado);
        boton.setImageResource(img_estado);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(estado.equals("0")){
                    try {
                        String respuesta = new PostRCI().execute("D"+pin,"high").get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }else if(estado.equals("1")){
                    try {
                        String respuesta = new PostRCI().execute("D"+pin,"low").get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    ArrayList<HashMap<String, String>> string_devuelto = new PideString().execute("/"+pin).get();
                    String estado2 = string_devuelto.get(0).get("data");
                    estado = estado2;
                    Log.d("***---***ESTADO2", estado2);
                    if (estado.equals("0")){
                        boton.setImageResource(R.drawable.apagado);
                    }else if (estado.equals("1")){
                        boton.setImageResource(R.drawable.encendido);
                    }else{
                        boton.setImageResource(R.drawable.error_conexion);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
