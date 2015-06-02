package com.example.milaronix.milkeo.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.milaronix.milkeo.ControlBDDispositivos;
import com.example.milaronix.milkeo.DetalleDispositivo;
import com.example.milaronix.milkeo.Dispositivo;
import com.example.milaronix.milkeo.MainActivity;
import com.example.milaronix.milkeo.PideString;
import com.example.milaronix.milkeo.R;
import com.example.milaronix.milkeo.adapters.DispositivosAdapter;
import com.example.milaronix.milkeo.ItemDispositivos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Dispositivos extends Fragment{

    static View rootView = null;
    Dispositivo dispositivo = null;

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

        ControlBDDispositivos bd2 = new ControlBDDispositivos(rootView.getContext());
        List<Dispositivo> lista2 = bd2.getAllDispositivos();

        for (Dispositivo cn: lista2){
            Log.d("***Id: ", cn.getId()+"");
            Log.d("***Nombre: ", cn.getNombre()+"");
            Log.d("***Estado: ", cn.getEstado()+"");
            Log.d("***Pin: ", cn.getPin()+"");
            Log.d("***Imagen: ", cn.getImagen()+"");
            Log.d("***Imagen Estado: ", cn.getImg_estado()+"");
        }

        for(int i = 0; i < string_devuelto.size(); i++){
            String estado = string_devuelto.get(i).get("data");
            String pin = string_devuelto.get(i).get("id");
            for(int cn = 0; cn < lista2.size(); cn++){
                if(lista2.get(cn).getPin().equals(pin)){
                    if (estado.equals("0")){
                        lista2.get(cn).setEstado("0");
                    }else if (estado.equals("1")){
                        lista2.get(cn).setEstado("1");
                    }else{
                        lista2.get(cn).setEstado("999");
                    }
                    Log.d("<*****PINES*****>","P1: "+pin+" P2: "+lista2.get(cn).getPin());
                    Log.d("<*****ESTADOS*****>","E1: "+estado+" E2: "+lista2.get(cn).getEstado());
                    bd2.updateDispositivo(lista2.get(cn));
                }

            }
        }
        bd2.close();

        final ArrayList<Dispositivo> data2 = new ArrayList<Dispositivo>();

        ControlBDDispositivos bd = new ControlBDDispositivos(rootView.getContext());
        List<Dispositivo> dispositivos = bd.getAllDispositivos();

        bd.close();
        Log.d("********Regreso del Query","");

        for (int i = 0; i < dispositivos.size(); i++){
            //data.add(new ItemDispositivos(items.get(i)));

            //if(dispositivos.get(i).getId() != 1){
            //    bd.borrarDispositivo(dispositivos.get(i));
            //}else {
                data2.add(dispositivos.get(i));
            //}

            //data.add(new ItemDispositivos(items.get(i).imagen,items.get(i).nombre,items.get(i).img_estado));
        }

        DispositivosAdapter adapter = new DispositivosAdapter(rootView.getContext(),R.layout.adapter_lista_dispositivos,data2);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(rootView.getContext(), DetalleDispositivo.class);
                myIntent.putExtra("id", data2.get(position).getId());
                myIntent.putExtra("nombre", data2.get(position).getNombre());
                myIntent.putExtra("pin", data2.get(position).getPin());
                myIntent.putExtra("imagen", data2.get(position).getImagen());
                myIntent.putExtra("estado", data2.get(position).getEstado());
                myIntent.putExtra("img_estado", data2.get(position).getImg_estado());
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
