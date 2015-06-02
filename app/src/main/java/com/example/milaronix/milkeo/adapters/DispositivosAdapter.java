package com.example.milaronix.milkeo.adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milaronix.milkeo.Dispositivo;
import com.example.milaronix.milkeo.R;
import com.example.milaronix.milkeo.ItemDispositivos;

import java.util.ArrayList;

/**
 * Created by milaronix on 30/05/15.
 */
public class DispositivosAdapter extends ArrayAdapter{
    Context context;
    int layoutResourceId;
    ArrayList<Dispositivo> data = null;

    public DispositivosAdapter(Context context, int layoutResourceId, ArrayList<Dispositivo> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.estado = (ImageView) row.findViewById(R.id.estado);

            row.setTag(holder);
        }
        else
        {
            holder = (ItemHolder)row.getTag();
        }

        Dispositivo item = data.get(position);
        holder.txtTitle.setText(item.getNombre());

        if(item.getEstado().equals("0")){
            holder.estado.setImageResource(R.drawable.apagado);
        }else if(item.getEstado().equals("1")){
            holder.estado.setImageResource(R.drawable.encendido);
        }else if(item.getEstado().equals("999")){
            holder.estado.setImageResource(R.drawable.error_conexion);
        }

        if(item.getImagen() == null){
            holder.imgIcon.setImageResource(R.drawable.no_imagen);
        }else{
            holder.imgIcon.setImageURI(Uri.parse(item.getImagen()));
        }

        return row;
    }

    static class ItemHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        ImageView estado;
    }
}
