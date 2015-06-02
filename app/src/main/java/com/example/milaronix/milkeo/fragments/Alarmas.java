package com.example.milaronix.milkeo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.milaronix.milkeo.AgregarDispositivo;
import com.example.milaronix.milkeo.R;
import com.example.milaronix.milkeo.SetAlarma;

public class Alarmas extends Fragment {
    static View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_alarmas, container, false);
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
                Intent myIntent = new Intent(rootView.getContext(), SetAlarma.class);
                startActivity(myIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
