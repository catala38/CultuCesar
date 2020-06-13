package com.example.cultucesar.Data;

public class CultuCesarContract {

    //CONTRACT EVENTOS
    public static String TABLA_EVENTO = "eventos";
    public static String CODIGO_EVENTO = "codigo";
    public static String MUNICIPIO_EVENTO = "municipio";
    public static String NOMBRE_EVENTO = "nombre";
    public static String INFO_EVENTO = "infoGeneral";
    public static String FECHA_EVENTO = "fechaEvento";
    public static String VALOR_ESTIMADO = "valorEvento";
    public static String TELEFONO = "telefonoEvento";
    public static String WEB = "sitioWeb";
    public static String FOTO_EVENTO= "foto";

    //CONTRACT SITIOS INTERES
    public static String TABLA_SITIO_INTERES = "sitiosInteres";
    public static String CODIGO_SITIO_INTERES = "codigo";
    public static String TIPO_SITIO_INTERES = "tipo";
    public static String MUNICIPIO_SITIO_INTERES = "municipio";
    public static String NOMBRE_SITIO_INTERES = "nombre";
    public static String INFO_SITIO_INTERES = "infoSitioInteres";
    public static String VALOR_RANGO_SITIO_INTERES = "valorSitioInteres";
    public static String TELEFONO_SITIO_INTERES = "telefonoSitioInteres";
    public static String WEB_SITIO_INTERES = "sitioWebInteres";
    public static String FOTO_SITIO_INTERES = "fotoSitioInteres";
    public static String IMG_DETALLE_SITIO1_INTERES = "imageDetalleS1Interes";
    public static String IMG_DETALLE_SITIO2_INTERES = "imageDetalleS2Interes";
    public static String IMG_DETALLE_SITIO3_INTERES = "imageDetalleS3Interes";

    //CONTRACT SITIOS RECREATIVOS
    public static String TABLA_SITIO_RECREATIVO = "sitiosRecreativo";
    public static String CODIGO_SITIO_RECREATIVO = "codigo";
    public static String TIPO_SITIO_RECREATIVO = "tipo";
    public static String MUNICIPIO_SITIO_RECREATIVO = "municipio";
    public static String NOMBRE_SITIO_RECREATIVO = "nombre";
    public static String INFO_SITIO_RECREATIVO = "infoSitioRecreativo";
    public static String DETALLE_SITIO_RECREATIVO = "detalleSitioRecreativo";
    public static String IMG_DETALLE_RECREATIVO1 = "imageDetalleS1Recreativo";
    public static String IMG_DETALLE_RECREATIVO2 = "imageDetalleS2Recreativo";
    public static String IMG_DETALLE_RECREATIVO3 = "imageDetalleS3Recreativo";


    //CONTRACT ACTIVIDADES
    public static String TABLA_ACTIVIDAD = "actividades";
    public static String CODIGO_ACTIVIDAD = "codigo";
    public static String MUNICIPIO_ACTIVIDAD = "municipio";
    public static String NOMBRE_ACTIVIDAD = "nombreActividad";
    public static String INFO_ACTIVIDAD = "infoG";
    public static String DETALLE_ACTIVIDAD = "detalleActividad";
    public static String FOTO_ACTIVIDAD= "foto";
    public static String IMG_DETALLE_ACTIVIDAD = "imageDetalle";

    //CONTRACT MUNUCIPIO DETALLE(JUNGLARES,HISTORIA,LEYENDAS)
    public static String TABLA_DETALLE_M = "actividades";
    public static String CODIGO_DETALLE_M = "codigo";
    public static String MUNICIPIO_DETALLE_M = "municipio";
    public static String NOMBRE_DETALLE_M = "nombreDetalleM";
    public static String INFO_DETALLE_M = "infoGM";
    public static String DESCRIPCION_DETALLE_M = "descripcionDetalleM";
    public static String FOTO_DETALLE_M= "fotoDetalleM";
    public static String IMG_DETALLE_M = "imageDetalleM";


    //CREAR TABLA EVENTO
    public static final  String CREAR_TABLA_EVENTO="CREATE TABLE" + " " + TABLA_EVENTO + " " +
            "(" +CODIGO_EVENTO+" "+ "INTEGER,"+ MUNICIPIO_EVENTO+" " +" TEXT," +  NOMBRE_EVENTO+" " +" TEXT,"+ INFO_EVENTO+" " +" TEXT,"+
            FECHA_EVENTO+" " +" TEXT," + VALOR_ESTIMADO+" " +" TEXT," + TELEFONO+" " +" TEXT," + WEB+" " +" TEXT," +
            FOTO_EVENTO+" " +" INTEGER)";

    //CREAR TABLA SITIO INTERES
    public static final  String CREAR_TABLA_SITIO_INTERES="CREATE TABLE" + " " + TABLA_SITIO_INTERES + " " +
            "(" +CODIGO_SITIO_INTERES+" "+ "INTEGER,"+ TIPO_SITIO_INTERES + " "+" TEXT,"+ MUNICIPIO_SITIO_INTERES+" " +" TEXT," +  NOMBRE_SITIO_INTERES+" " +" TEXT,"+ INFO_SITIO_INTERES+" " +" TEXT,"
            + VALOR_RANGO_SITIO_INTERES+" " +" TEXT," + TELEFONO_SITIO_INTERES+" " +" TEXT,"+ WEB_SITIO_INTERES +" " +" TEXT," + FOTO_SITIO_INTERES+" " +" INTEGER,"+IMG_DETALLE_SITIO1_INTERES+" "+ "INTEGER,"+
            IMG_DETALLE_SITIO2_INTERES +" "+ "INTEGER,"+IMG_DETALLE_SITIO3_INTERES+" "+ "INTEGER)";

    //CREAR TABLA SITIO RECREATIVO
    public static final  String CREAR_TABLA_SITIO_RECREATIVO="CREATE TABLE" + " " + TABLA_SITIO_RECREATIVO + " " +
            "(" +CODIGO_SITIO_RECREATIVO+" "+ "INTEGER,"+ TIPO_SITIO_RECREATIVO + " "+" TEXT,"+ MUNICIPIO_SITIO_RECREATIVO+" " +" TEXT," +  NOMBRE_SITIO_RECREATIVO+" " +" TEXT,"+ INFO_SITIO_RECREATIVO+" " +" TEXT,"
            + DETALLE_SITIO_RECREATIVO+" " +" TEXT," + IMG_DETALLE_RECREATIVO1 +" "+ "INTEGER,"+ IMG_DETALLE_RECREATIVO2 +" "+ "INTEGER,"+ IMG_DETALLE_RECREATIVO3 +" "+ "INTEGER)";

    //CREAR TABLA ACTIVIDAD
    public static final  String CREAR_TABLA_ACTIVIDAD="CREATE TABLE" +
            " " + TABLA_ACTIVIDAD + " (" +CODIGO_ACTIVIDAD+" "+
            "INTEGER,"+ MUNICIPIO_ACTIVIDAD+" " +" TEXT," +  NOMBRE_ACTIVIDAD+" " +" TEXT,"+ INFO_ACTIVIDAD+" " +" TEXT,"+ DETALLE_ACTIVIDAD+" " +" TEXT,"
            + FOTO_ACTIVIDAD+" " +" INTEGER,"+IMG_DETALLE_ACTIVIDAD+" "+ "INTEGER,)";

    //CREAR TABLA DETALLE MUNICIPIO
    public static final  String CREAR_TABLA_DETALLE_M="CREATE TABLE" +
            " " + TABLA_DETALLE_M + " (" +CODIGO_DETALLE_M+" "+
            "INTEGER,"+ MUNICIPIO_DETALLE_M+" " +" TEXT," +  NOMBRE_DETALLE_M+" " +" TEXT,"+ INFO_DETALLE_M+" " +" TEXT,"+ DESCRIPCION_DETALLE_M+" " +" TEXT,"
            + FOTO_DETALLE_M+" " +" INTEGER,"+IMG_DETALLE_M+" "+ "INTEGER,)";
}
