package com.example.milaronix.milkeo.fragments;

import android.app.ListActivity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.example.milaronix.milkeo.R;

import java.util.ArrayList;

public class Dispositivos extends Fragment{

    static View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dispositivos, container, false);
        final ListView lista = (ListView) rootView.findViewById(R.id.lista_disp);
        ArrayList<String> prueba = new ArrayList<String>();
        prueba.add("el uno");
        prueba.add("el dos");
        prueba.add("el tres");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,prueba);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valor = (String) lista.getItemAtPosition(position);

                Toast.makeText(rootView.getContext(), "presiono: "+valor, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
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
                Toast toast = Toast.makeText(rootView.getContext(), "agregar dispositivo", Toast.LENGTH_SHORT);
                toast.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
