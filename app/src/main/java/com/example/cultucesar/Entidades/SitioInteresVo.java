package com.example.cultucesar.Entidades;

import java.io.Serializable;

public class SitioInteresVo implements Serializable {

    private  int  codigo;
    private  String municipio;
    private  String tipo;
    private  String nombreSitioInteres;
    private  String infoSitioInteres;
    private  String valorSitioInteres;
    private  String telefonoSitioInteres;
    private  String sitioWebInteres;
    private  int fotoSitioInteres;
    private  int imageDetalleS1Interes;
    private  int imageDetalleS2Interes;
    private  int imageDetalleS3Interes;

    public SitioInteresVo(int codigo, String municipio, String tipo, String nombreSitioInteres, String infoSitioInteres, String valorSitioInteres, String telefonoSitioInteres, String sitioWebInteres, int fotoSitioInteres, int imageDetalleS1Interes, int imageDetalleS2Interes, int imageDetalleS3Interes) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.tipo = tipo;
        this.nombreSitioInteres = nombreSitioInteres;
        this.infoSitioInteres = infoSitioInteres;
        this.valorSitioInteres = valorSitioInteres;
        this.telefonoSitioInteres = telefonoSitioInteres;
        this.sitioWebInteres = sitioWebInteres;
        this.fotoSitioInteres = fotoSitioInteres;
        this.imageDetalleS1Interes = imageDetalleS1Interes;
        this.imageDetalleS2Interes = imageDetalleS2Interes;
        this.imageDetalleS3Interes = imageDetalleS3Interes;
    }


    public SitioInteresVo(){}



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

    public String getNombreSitioInteres() {
        return nombreSitioInteres;
    }

    public void setNombreSitioInteres(String nombreSitioInteres) {
        this.nombreSitioInteres = nombreSitioInteres;
    }

    public String getInfoSitioInteres() {
        return infoSitioInteres;
    }

    public void setInfoSitioInteres(String infoSitioInteres) {
        this.infoSitioInteres = infoSitioInteres;
    }

    public String getValorSitioInteres() {
        return valorSitioInteres;
    }

    public void setValorSitioInteres(String valorSitioInteres) {
        this.valorSitioInteres = valorSitioInteres;
    }

    public String getTelefonoSitioInteres() {
        return telefonoSitioInteres;
    }

    public void setTelefonoSitioInteres(String telefonoSitioInteres) {
        this.telefonoSitioInteres = telefonoSitioInteres;
    }

    public String getSitioWebInteres() {
        return sitioWebInteres;
    }

    public void setSitioWebInteres(String sitioWebInteres) {
        this.sitioWebInteres = sitioWebInteres;
    }

    public int getFotoSitioInteres() {
        return fotoSitioInteres;
    }

    public void setFotoSitioInteres(int fotoSitioInteres) {
        this.fotoSitioInteres = fotoSitioInteres;
    }

    public int getImageDetalleS1Interes() {
        return imageDetalleS1Interes;
    }

    public void setImageDetalleS1Interes(int imageDetalleS1Interes) {
        this.imageDetalleS1Interes = imageDetalleS1Interes;
    }

    public int getImageDetalleS2Interes() {
        return imageDetalleS2Interes;
    }

    public void setImageDetalleS2Interes(int imageDetalleS2Interes) {
        this.imageDetalleS2Interes = imageDetalleS2Interes;
    }

    public int getImageDetalleS3Interes() {
        return imageDetalleS3Interes;
    }

    public void setImageDetalleS3Interes(int imageDetalleS3Interes) {
        this.imageDetalleS3Interes = imageDetalleS3Interes;
    }




}
