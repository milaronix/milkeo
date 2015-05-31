package com.example.milaronix.milkeo;

/**
 * Created by milaronix on 30/05/15.
 */
public class ItemDispositivos {
    public int icon;
    public String title;
    public int estado;
    public ItemDispositivos(){
        super();
    }

    public ItemDispositivos(int icon, String title, int estado) {
        super();
        this.icon = icon;
        this.title = title;
        this.estado = estado;
    }
}
