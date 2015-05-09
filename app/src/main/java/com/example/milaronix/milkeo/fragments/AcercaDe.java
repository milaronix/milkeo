package com.example.milaronix.milkeo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.milaronix.milkeo.R;

public class AcercaDe extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_acerca_de, container, false);

        TextView mail = (TextView) rootView.findViewById(R.id.mail);
        Linkify.addLinks(mail, Linkify.ALL);
        TextView tel = (TextView) rootView.findViewById(R.id.telefono);
        Linkify.addLinks(tel, Linkify.ALL);

        return rootView;
    }

}
