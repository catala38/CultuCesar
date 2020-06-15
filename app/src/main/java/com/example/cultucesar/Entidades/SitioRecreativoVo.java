package com.example.cultucesar.Entidades;

import java.io.Serializable;

public class SitioRecreativoVo implements Serializable {

    private  int  codigo;
    private  String municipio;
    private  String tipo;
    private  String nombreSitioRecreativo;
    private  String infoSitioRecreativo;
    private  String detalleSitioRecreativo;
    private  int imageDetalleS1Recreativo;
    private  int imageDetalleS2Recreativo;
    private  int imageDetalleS3Recreativo;


    public SitioRecreativoVo(int codigo, String municipio, String tipo, String nombreSitioRecreativo, String infoSitioRecreativo, String detalleSitioRecreativo, int imageDetalleS1Recreativo, int imageDetalleS2Recreativo, int imageDetalleS3Recreativo) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.tipo = tipo;
        this.nombreSitioRecreativo = nombreSitioRecreativo;
        this.infoSitioRecreativo = infoSitioRecreativo;
        this.detalleSitioRecreativo = detalleSitioRecreativo;
        this.imageDetalleS1Recreativo = imageDetalleS1Recreativo;
        this.imageDetalleS2Recreativo = imageDetalleS2Recreativo;
        this.imageDetalleS3Recreativo = imageDetalleS3Recreativo;
    }

    public SitioRecreativoVo(){}

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreSitioRecreativo() {
        return nombreSitioRecreativo;
    }

    public void setNombreSitioRecreativo(String nombreSitioRecreativo) {
        this.nombreSitioRecreativo = nombreSitioRecreativo;
    }

    public String getInfoSitioRecreativo() {
        return infoSitioRecreativo;
    }

    public void setInfoSitioRecreativo(String infoSitioRecreativo) {
        this.infoSitioRecreativo = infoSitioRecreativo;
    }

    public String getDetalleSitioRecreativo() {
        return detalleSitioRecreativo;
    }

    public void setDetalleSitioRecreativo(String detalleSitioRecreativo) {
        this.detalleSitioRecreativo = detalleSitioRecreativo;
    }

    public int getImageDetalleS1Recreativo() {
        return imageDetalleS1Recreativo;
    }

    public void setImageDetalleS1Recreativo(int imageDetalleS1Recreativo) {
        this.imageDetalleS1Recreativo = imageDetalleS1Recreativo;
    }

    public int getImageDetalleS2Recreativo() {
        return imageDetalleS2Recreativo;
    }

    public void setImageDetalleS2Recreativo(int imageDetalleS2Recreativo) {
        this.imageDetalleS2Recreativo = imageDetalleS2Recreativo;
    }

    public int getImageDetalleS3Recreativo() {
        return imageDetalleS3Recreativo;
    }

    public void setImageDetalleS3Recreativo(int imageDetalleS3Recreativo) {
        this.imageDetalleS3Recreativo = imageDetalleS3Recreativo;
    }











}
