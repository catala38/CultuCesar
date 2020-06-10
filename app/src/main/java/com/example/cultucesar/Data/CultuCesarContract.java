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


    //CREAR TABLA EVENTO
    public static final  String CREAR_TABLA_EVENTO="CREATE TABLE" +
            " " + TABLA_EVENTO + " (" +CODIGO_EVENTO+" "+
            "INTEGER,"+ MUNICIPIO_EVENTO+" " +" TEXT," +  NOMBRE_EVENTO+" " +" TEXT,"+ INFO_EVENTO+" " +" TEXT,"+
            FECHA_EVENTO+" " +" TEXT," + VALOR_ESTIMADO+" " +" TEXT," + TELEFONO+" " +" TEXT," + WEB+" " +" TEXT," +
            FOTO_EVENTO+" " +" INTEGER)";
}
