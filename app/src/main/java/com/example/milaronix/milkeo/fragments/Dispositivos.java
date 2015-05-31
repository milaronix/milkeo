package com.example.milaronix.milkeo.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.milaronix.milkeo.MainActivity;
import com.example.milaronix.milkeo.R;
import com.example.milaronix.milkeo.adapters.DispositivosAdapter;
import com.example.milaronix.milkeo.ItemDispositivos;

import java.util.ArrayList;

public class Dispositivos extends Fragment{

    static View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dispositivos, container, false);
        final ListView lista = (ListView) rootView.findViewById(R.id.lista_disp);
        final ArrayList<String> prueba = new ArrayList<String>();
        prueba.add("el uno");
        prueba.add("el dos");
        prueba.add("el tres");

        final ArrayList<ItemDispositivos> data = new ArrayList<ItemDispositivos>();
        data.add(new ItemDispositivos(R.drawable.no_imagen,"uno",R.drawable.apagado));
        data.add(new ItemDispositivos(R.drawable.no_imagen,"dos",R.drawable.apagado));
        data.add(new ItemDispositivos(R.drawable.no_imagen,"tres",R.drawable.apagado));

        DispositivosAdapter adapter = new DispositivosAdapter(rootView.getContext(),R.layout.adapter_lista_dispositivos,data);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(rootView.getContext(), DetalleDispositivo.class);
                myIntent.putExtra("nombre", data.get(position).title);
                myIntent.putExtra("imagen", data.get(position).icon);
                myIntent.putExtra("posicion", position);
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
