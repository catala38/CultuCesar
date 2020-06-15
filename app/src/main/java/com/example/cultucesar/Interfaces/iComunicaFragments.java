package com.example.cultucesar.Interfaces;

import com.example.cultucesar.Entidades.DestinosVo;
import com.example.cultucesar.Entidades.EventoVo;
import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;

public interface iComunicaFragments {
    //esta interface se encarga de realizar la comunicacion entre la lista de personas y el detalle
    public void enviarDestinos(DestinosVo destinosVo);
    public void enviarEventoCultural(EventoVo eventoVo);
    public void enviarSitioInteres(SitioInteresVo sitioInteresVo);
    public void enviarSitioRecreativo(SitioRecreativoVo sitioRecreativoVo);

}
