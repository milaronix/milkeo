package com.example.milaronix.milkeo.adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milaronix.milkeo.R;
import com.example.milaronix.milkeo.ItemDispositivos;

import java.util.ArrayList;

/**
 * Created by milaronix on 30/05/15.
 */
public class DispositivosAdapter extends ArrayAdapter{
    Context context;
    int layoutResourceId;
    ArrayList<ItemDispositivos> data = null;

    public DispositivosAdapter(Context context, int layoutResourceId, ArrayList<ItemDispositivos> data) {
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

        ItemDispositivos item = data.get(position);
        holder.txtTitle.setText(item.dipositivo.nombre);
        holder.imgIcon.setImageResource(item.dipositivo.imagen);
        holder.estado.setImageResource(item.dipositivo.img_estado);

        return row;
    }

    static class ItemHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        ImageView estado;
    }
}
