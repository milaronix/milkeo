package com.example.milaronix.milkeo;

/**
 * Created by milaronix on 31/05/15.
 */
public class Dispositivo {
    public int imagen;
    public String nombre;
    public String pin;
    public String estado;
    public int img_estado;

    public Dispositivo(){
        super();
    }

    public Dispositivo(int imagen, String nombre, String pin, String estado, int img_estado) {
        super();
        this.imagen = imagen;
        this.nombre = nombre;
        this.pin = pin;
        this.estado = estado;
        this.img_estado = img_estado;
    }
}
