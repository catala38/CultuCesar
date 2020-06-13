package com.example.cultucesar.Entidades;

public class DetalleMunicipioVo {

    private  int  codigo;
    private  String municipio;
    private  String nombreDetalleM;
    private  String infoGM;
    private  String descripcionDetalleM;
    private  int fotoDetalleM;
    private  int imageDetalleM;

    public DetalleMunicipioVo(int codigo, String municipio, String nombreDetalleM, String infoGM, String descripcionDetalleM, int fotoDetalleM, int imageDetalleM) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.nombreDetalleM = nombreDetalleM;
        this.infoGM = infoGM;
        this.descripcionDetalleM = descripcionDetalleM;
        this.fotoDetalleM = fotoDetalleM;
        this.imageDetalleM = imageDetalleM;
    }

    public DetalleMunicipioVo(){}


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

    public String getNombreDetalleM() {
        return nombreDetalleM;
    }

    public void setNombreDetalleM(String nombreDetalleM) {
        this.nombreDetalleM = nombreDetalleM;
    }

    public String getInfoGM() {
        return infoGM;
    }

    public void setInfoGM(String infoGM) {
        this.infoGM = infoGM;
    }

    public String getDescripcionDetalleM() {
        return descripcionDetalleM;
    }

    public void setDescripcionDetalleM(String descripcionDetalleM) {
        this.descripcionDetalleM = descripcionDetalleM;
    }

    public int getFotoDetalleM() {
        return fotoDetalleM;
    }

    public void setFotoDetalleM(int fotoDetalleM) {
        this.fotoDetalleM = fotoDetalleM;
    }

    public int getImageDetalleM() {
        return imageDetalleM;
    }

    public void setImageDetalleM(int imageDetalleM) {
        this.imageDetalleM = imageDetalleM;
    }





}
