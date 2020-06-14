package com.example.cultucesar.Entidades;

import java.io.Serializable;

public class EventoVo implements Serializable {

    private  int  codigo;
    private  String municipio;
    private  String nombreEvento;
    private  String infoGeneral;
    private  String fechaEvento;
    private  String valorEvento;
    private  String telefonoEvento;
    private  String sitioWeb;
    private  int imagen;

    public EventoVo(int codigo, String municipio, String nombreEvento, String infoGeneral, String fechaEvento, String valorEvento, String telefonoEvento, String sitioWeb, int imagen) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.nombreEvento = nombreEvento;
        this.infoGeneral = infoGeneral;
        this.fechaEvento = fechaEvento;
        this.valorEvento = valorEvento;
        this.telefonoEvento = telefonoEvento;
        this.sitioWeb = sitioWeb;
        this.imagen = imagen;
    }

    public EventoVo(){}

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

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getInfoGeneral() {
        return infoGeneral;
    }

    public void setInfoGeneral(String infoGeneral) {
        this.infoGeneral = infoGeneral;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getValorEvento() {
        return valorEvento;
    }

    public void setValorEvento(String valorEvento) {
        this.valorEvento = valorEvento;
    }

    public String getTelefonoEvento() {
        return telefonoEvento;
    }

    public void setTelefonoEvento(String telefonoEvento) {
        this.telefonoEvento = telefonoEvento;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
