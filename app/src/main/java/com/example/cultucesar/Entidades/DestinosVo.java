package com.example.cultucesar.Entidades;

import java.io.Serializable;

public class DestinosVo implements Serializable {
    private String nombre;
    private String info;
    private int imagenid;
    private String infoG;
    private String ubicacion;

    public DestinosVo(String nombre, String info, int imagenid, String infoG, String ubicacion) {
        this.nombre = nombre;
        this.info = info;
        this.imagenid = imagenid;

        this.infoG = infoG;
        this.ubicacion = ubicacion;
    }


    public DestinosVo(){}

    public String getInfoG() {
        return infoG;
    }

    public void setInfoG(String infoG) {
        this.infoG = infoG;
    }


    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImagenid() {
        return imagenid;
    }

    public void setImagenid(int imagenid) {
        this.imagenid = imagenid;
    }
}
