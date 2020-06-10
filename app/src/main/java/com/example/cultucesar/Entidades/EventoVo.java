package com.example.cultucesar.Entidades;

public class EventoVo {

    private  int  codigo;
    private  String municipio;
    private  String nombreEvento;
    private  String infoGeneral;
    private  String fechaEvento;
    private  String valorEvento;
    private  String telefonoEvento;
    private  String sitioWeb;
    private  int foto;

    public EventoVo(int codigo, String municipio, String nombreEvento, String infoGeneral, String fechaEvento, String valorEvento, String telefonoEvento, String sitioWeb, int foto) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.nombreEvento = nombreEvento;
        this.infoGeneral = infoGeneral;
        this.fechaEvento = fechaEvento;
        this.valorEvento = valorEvento;
        this.telefonoEvento = telefonoEvento;
        this.sitioWeb = sitioWeb;
        this.foto = foto;
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

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
