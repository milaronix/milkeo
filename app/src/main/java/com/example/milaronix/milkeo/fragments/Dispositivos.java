package com.example.milaronix.milkeo.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.milaronix.milkeo.AgregarDispositivo;
import com.example.milaronix.milkeo.DetalleDispositivo;
import com.example.milaronix.milkeo.Dispositivo;
import com.example.milaronix.milkeo.MainActivity;
import com.example.milaronix.milkeo.PideString;
import com.example.milaronix.milkeo.R;
import com.example.milaronix.milkeo.adapters.DispositivosAdapter;
import com.example.milaronix.milkeo.ItemDispositivos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Dispositivos extends Fragment{

    static View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dispositivos, container, false);
        final ListView lista = (ListView) rootView.findViewById(R.id.lista_disp);
        ArrayList<HashMap<String,String>> string_devuelto = null;

        try {
            string_devuelto = new PideString().execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final ArrayList<Dispositivo> items = new ArrayList<Dispositivo>();
        Dispositivo dispositivo = null;

        for(int i = 0; i < string_devuelto.size(); i++){
            String estado = string_devuelto.get(i).get("data");
            int img_estado = 0;
            if (estado.equals("0")){
                img_estado = R.drawable.apagado;
            }else if (estado.equals("1")){
                img_estado = R.drawable.encendido;
            }else{
                img_estado = R.drawable.error_conexion;
            }
            dispositivo = new Dispositivo(R.drawable.tomacorriente,"Tomacorriente "+(i+1),string_devuelto.get(i).get("id"),estado,img_estado);
            items.add(dispositivo);
        }

        final ArrayList<ItemDispositivos> data = new ArrayList<ItemDispositivos>();

        for (int i = 0; i < items.size(); i++){
            data.add(new ItemDispositivos(items.get(i)));

            //data.add(new ItemDispositivos(items.get(i).imagen,items.get(i).nombre,items.get(i).img_estado));
        }

        DispositivosAdapter adapter = new DispositivosAdapter(rootView.getContext(),R.layout.adapter_lista_dispositivos,data);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(rootView.getContext(), DetalleDispositivo.class);
                myIntent.putExtra("nombre", data.get(position).dipositivo.nombre);
                myIntent.putExtra("pin", data.get(position).dipositivo.pin);
                myIntent.putExtra("imagen", data.get(position).dipositivo.imagen);
                myIntent.putExtra("estado", data.get(position).dipositivo.estado);
                myIntent.putExtra("img_estado", data.get(position).dipositivo.img_estado);
                startActivity(myIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dispositvos, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.agregar:
                Intent myIntent = new Intent(rootView.getContext(), AgregarDispositivo.class);
                startActivity(myIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
