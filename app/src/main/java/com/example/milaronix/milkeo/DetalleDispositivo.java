package com.example.milaronix.milkeo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
public class DetalleDispositivo extends ActionBarActivity{
    View rootView = null;
    int id = 0;
    boolean bandera = false;
    Dispositivo dispositivo = new Dispositivo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_dispositivo);


        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");

        ControlBDDispositivos bd = new ControlBDDispositivos(getApplicationContext());
        dispositivo = bd.getDispositivo(id);
        bd.close();
        Log.d("*********PRUEBA string", dispositivo.getNombre());

        ImageView imagen_i = (ImageView) findViewById(R.id.imagen);
        if(dispositivo.getImagen() == null){
            imagen_i.setImageResource(R.drawable.no_imagen);
        }else{
            imagen_i.setImageURI(Uri.parse(dispositivo.getImagen()));
        }

        final ImageButton boton = (ImageButton) findViewById(R.id.b_estado);
        if(dispositivo.getEstado().equals("0")){
            boton.setImageResource(R.drawable.apagado);
        }else if(dispositivo.getEstado().equals("1")){
            boton.setImageResource(R.drawable.encendido);
        }else if(dispositivo.getEstado().equals("999")){
            boton.setImageResource(R.drawable.error_conexion);
        }

        TextView el_nombre = (TextView) findViewById(R.id.el_nombre);
        TextView el_pin = (TextView) findViewById(R.id.el_pin);
        el_nombre.setText(dispositivo.getNombre());
        el_pin.setText(dispositivo.getPin());

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlBDDispositivos bd = new ControlBDDispositivos(getApplicationContext());
                if(bandera){dispositivo = bd.getDispositivo(id);}

                if(dispositivo.getEstado().equals("0")){
                    try {
                        new PostRCI().execute("D"+dispositivo.getPin(),"high").get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }else if(dispositivo.getEstado().equals("1")){
                    try {
                        new PostRCI().execute("D"+dispositivo.getPin(),"low").get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    ArrayList<HashMap<String, String>> string_devuelto = new PideString().execute("/"+dispositivo.getPin()).get();
                    dispositivo.setEstado(string_devuelto.get(0).get("data"));
                    Log.d("***---***ESTADO2", dispositivo.getEstado());
                    if (dispositivo.getEstado().equals("0")){
                        boton.setImageResource(R.drawable.apagado);
                    }else if (dispositivo.getEstado().equals("1")){
                        boton.setImageResource(R.drawable.encendido);
                    }else{
                        boton.setImageResource(R.drawable.error_conexion);
                    }
                    bd.updateDispositivo(dispositivo);
                    bd.close();
                    bandera = true;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detalle_dispositivo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eliminar:
                ControlBDDispositivos bd = new ControlBDDispositivos(getApplicationContext());
                bd.borrarDispositivo(dispositivo);
                Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
