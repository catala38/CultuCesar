package com.example.cultucesar.Entidades;

import java.io.Serializable;

public class ActividadesVo implements Serializable {

    private  int  codigo;
    private  String municipio;
    private  String nombreActividad;
    private  String infoGeneral;
    private  String DetalleActividad;
    private  int imagenP;
    private  int imagenD;

    public ActividadesVo(int codigo, String municipio, String nombreActividad, String infoGeneral, String detalleActividad, int imagenP, int imagenD) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.nombreActividad = nombreActividad;
        this.infoGeneral = infoGeneral;
        this.DetalleActividad = detalleActividad;
        this.imagenP = imagenP;
        this.imagenD = imagenD;
    }

    public ActividadesVo(){}


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getInfoGeneral() {
        return infoGeneral;
    }

    public void setInfoGeneral(String infoGeneral) {
        this.infoGeneral = infoGeneral;
    }

    public String getDetalleActividad() {
        return DetalleActividad;
    }

    public void setDetalleActividad(String detalleActividad) {
        DetalleActividad = detalleActividad;
    }

    public int getImagenP() {
        return imagenP;
    }

    public void setImagenP(int imagenP) {
        this.imagenP = imagenP;
    }

    public int getImagenD() {
        return imagenD;
    }

    public void setImagenD(int imagenD) {
        this.imagenD = imagenD;
    }

}
