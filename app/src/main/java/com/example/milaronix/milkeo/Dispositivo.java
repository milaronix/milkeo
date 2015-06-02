package com.example.milaronix.milkeo;

/**
 * Created by milaronix on 31/05/15.
 */
public class Dispositivo {
    String imagen;
    String nombre;
    String pin;
    String estado;
    int img_estado;
    int id;

    public Dispositivo(){

    }

    public Dispositivo(int id, String imagen, String nombre, String pin, String estado, int img_estado) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.pin = pin;
        this.estado = estado;
        this.img_estado = img_estado;
    }

    public Dispositivo(String imagen, String nombre, String pin, String estado, int img_estado) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.pin = pin;
        this.estado = estado;
        this.img_estado = img_estado;
    }

    public void setId (int id){
        this.id = id;
    }

    public void setImagen (String imagen){
        this.imagen= imagen;
    }

    public void setNombre (String nombre){
        this.nombre = nombre;
    }

    public void setPin (String pin){
        this.pin = pin;
    }

    public void setEstado (String estado){
        this.estado = estado;
    }

    public void setImg_estado (int img_estado){
        this.img_estado = img_estado;
    }

    public int getId (){
        return this.id;
    }

    public String getImagen (){
        return this.imagen;
    }

    public String getNombre (){
        return this.nombre;
    }

    public String getPin (){
        return this.pin;
    }

    public String getEstado (){
        return this.estado;
    }

    public int getImg_estado (){
        return this.img_estado;
    }
}
