package com.example.cultucesar.Fragments.SitioInteres;
import android.app.Activity;
import android.content.ContentUris;
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
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultucesar.Adaptadores.AdapterEventosCulturales;
import com.example.cultucesar.Adaptadores.AdapterSitioInteres;
import com.example.cultucesar.Data.ConexionSQLiteEventoHelper;
import com.example.cultucesar.Data.ConexionSQLiteSitiosInteresHelper;
import com.example.cultucesar.Entidades.EventoVo;
import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.Interfaces.iComunicaFragments;
import com.example.cultucesar.R;
import com.example.cultucesar.seleccionar_destino;

import java.util.ArrayList;


public class SitioInteresFragment extends Fragment {

    String destino = seleccionar_destino.destino;
    AdapterSitioInteres adapterSitioInteres;
    RecyclerView recyclerSitioInteres;
    ConexionSQLiteSitiosInteresHelper conn;
    ArrayList<SitioInteresVo> listaSitioInteres;
    Spinner comboSitioInteres;
    String TipoSitioInteres;

    //Crear referencias para poder realizar la comunicacion entre el fragment detalle
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sitio_interes_fragment,container,false);
        try{

            conn = new ConexionSQLiteSitiosInteresHelper(getContext(),"sitiosInteres",null,1);
            recyclerSitioInteres = view.findViewById(R.id.recyclerViewSitiosInteres);



            comboSitioInteres = (Spinner) view.findViewById(R.id.idSpinnerTipoSitioInteres);
            ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.comboTipoSitioInteres
            ,android.R.layout.simple_spinner_item);
            comboSitioInteres.setAdapter(adapter);

            comboSitioInteres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    TipoSitioInteres = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selecciono: "+TipoSitioInteres,Toast.LENGTH_SHORT).show();
                    ConsultarSitioInteres();
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

    public void ConsultarSitioInteres(){
        SQLiteDatabase db = conn.getReadableDatabase();
        listaSitioInteres = new ArrayList<>();
        SitioInteresVo sitioInteresVo = null;
        String[] result = new String[] {destino,TipoSitioInteres};
        Cursor cursor = db.rawQuery(" SELECT * FROM sitiosInteres WHERE municipio=? AND tipo=?", result);
        while(cursor.moveToNext()){
            sitioInteresVo = new SitioInteresVo();
            sitioInteresVo.setCodigo(cursor.getInt(0));
            sitioInteresVo.setTipo(cursor.getString(1));
            sitioInteresVo.setMunicipio(cursor.getString(2));
            sitioInteresVo.setNombreSitioInteres(cursor.getString(3));
            sitioInteresVo.setInfoSitioInteres(cursor.getString(4));
            sitioInteresVo.setValorSitioInteres(cursor.getString(5));
            sitioInteresVo.setTelefonoSitioInteres(cursor.getString(6));
            sitioInteresVo.setSitioWebInteres(cursor.getString(7));
            sitioInteresVo.setFotoSitioInteres(cursor.getInt(8));
            sitioInteresVo.setImageDetalleS1Interes(cursor.getInt(9));
            sitioInteresVo.setImageDetalleS2Interes(cursor.getInt(10));
            sitioInteresVo.setImageDetalleS3Interes(cursor.getInt(11));

            listaSitioInteres.add(sitioInteresVo);
        }
        recyclerSitioInteres.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterSitioInteres = new AdapterSitioInteres(getContext(),listaSitioInteres);
        recyclerSitioInteres.setAdapter(adapterSitioInteres);

        adapterSitioInteres.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó: "+  listaSitioInteres.get( recyclerSitioInteres.getChildAdapterPosition(view)).getNombreSitioInteres(), Toast.LENGTH_SHORT).show();
               interfaceComunicaFragments.enviarSitioInteres(listaSitioInteres.get(recyclerSitioInteres.getChildAdapterPosition(view)));
            }
        });
    }



}
