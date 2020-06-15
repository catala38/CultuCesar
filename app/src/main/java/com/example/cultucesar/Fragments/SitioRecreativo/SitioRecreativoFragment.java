package com.example.cultucesar.Fragments.SitioRecreativo;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultucesar.Adaptadores.AdapterSitioInteres;
import com.example.cultucesar.Adaptadores.AdapterSitioRecreativo;
import com.example.cultucesar.Data.ConexionSQLiteSitiosInteresHelper;
import com.example.cultucesar.Data.ConexionSQLiteSitiosRecreativoHelper;
import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;
import com.example.cultucesar.Interfaces.iComunicaFragments;
import com.example.cultucesar.R;
import com.example.cultucesar.seleccionar_destino;

import java.util.ArrayList;


public class SitioRecreativoFragment extends Fragment {

    String destino = seleccionar_destino.destino;
    AdapterSitioRecreativo adapterSitioRecreativo;
    RecyclerView recyclerSitioRecreativo;
    ConexionSQLiteSitiosRecreativoHelper conn;
    ArrayList<SitioRecreativoVo> listaSitioRecreativo;
    Spinner comboSitioRecreativo;
    String TipoSitioRecreativo;

    //Crear referencias para poder realizar la comunicacion entre el fragment detalle
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sitio_recreativo_fragment,container,false);
        try{

            conn = new ConexionSQLiteSitiosRecreativoHelper(getContext(),"sitiosRecreativo",null,1);
            recyclerSitioRecreativo = view.findViewById(R.id.recyclerViewSitiosRecreativo);



            comboSitioRecreativo = (Spinner) view.findViewById(R.id.idSpinnerTipoSitioRecreativo);
            ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.comboTipoSitioR
            ,android.R.layout.simple_spinner_item);
            comboSitioRecreativo.setAdapter(adapter);

            comboSitioRecreativo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    TipoSitioRecreativo = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selecciono: "+TipoSitioRecreativo,Toast.LENGTH_SHORT).show();
                    ConsultarSitioRecreativo();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }catch (Exception e){

        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //esto es necesario para establecer la comunicacion entre la lista y el detalle
        //si el contexto que le esta llegando es una instancia de un activity:
        if(context instanceof Activity){
            //voy a decirle a mi actividad que sea igual a dicho contesto. castin correspondiente:
            this.actividad= (Activity) context;
            ////que la interface icomunicafragments sea igual ese contexto de la actividad:
            interfaceComunicaFragments= (iComunicaFragments) this.actividad;
            //esto es necesario para establecer la comunicacion entre la lista y el detalle
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void ConsultarSitioRecreativo(){
        SQLiteDatabase db = conn.getReadableDatabase();
        listaSitioRecreativo = new ArrayList<>();
        SitioRecreativoVo sitioRecreativoVo = null;
        String[] result = new String[] {destino,TipoSitioRecreativo};
        Cursor cursor = db.rawQuery(" SELECT * FROM sitiosRecreativo WHERE municipio=? AND tipo=?", result);
        while(cursor.moveToNext()){
            sitioRecreativoVo = new SitioRecreativoVo();
            sitioRecreativoVo.setCodigo(cursor.getInt(0));
            sitioRecreativoVo.setTipo(cursor.getString(1));
            sitioRecreativoVo.setMunicipio(cursor.getString(2));
            sitioRecreativoVo.setNombreSitioRecreativo(cursor.getString(3));
            sitioRecreativoVo.setInfoSitioRecreativo(cursor.getString(4));
            sitioRecreativoVo.setDetalleSitioRecreativo(cursor.getString(5));
            sitioRecreativoVo.setImageDetalleS1Recreativo(cursor.getInt(6));
            sitioRecreativoVo.setImageDetalleS2Recreativo(cursor.getInt(7));
            sitioRecreativoVo.setImageDetalleS3Recreativo(cursor.getInt(8));

            listaSitioRecreativo.add(sitioRecreativoVo);
        }
        recyclerSitioRecreativo.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterSitioRecreativo = new AdapterSitioRecreativo(getContext(),listaSitioRecreativo);
        recyclerSitioRecreativo.setAdapter(adapterSitioRecreativo);

        adapterSitioRecreativo.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó: "+  listaSitioRecreativo.get( recyclerSitioRecreativo.getChildAdapterPosition(view)).getNombreSitioRecreativo(), Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarSitioRecreativo(listaSitioRecreativo.get(recyclerSitioRecreativo.getChildAdapterPosition(view)));
            }
        });
    }



}
