package com.example.cultucesar.Fragments.DetalleMunicipio;
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

import com.example.cultucesar.Adaptadores.AdapterDetalleMunicipio;
import com.example.cultucesar.Adaptadores.AdapterSitioRecreativo;
import com.example.cultucesar.Data.ConexionSQLiteDetalleMunicipioHelper;
import com.example.cultucesar.Data.ConexionSQLiteSitiosRecreativoHelper;
import com.example.cultucesar.Entidades.DetalleMunicipioVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;
import com.example.cultucesar.Interfaces.iComunicaFragments;
import com.example.cultucesar.R;
import com.example.cultucesar.seleccionar_destino;

import java.util.ArrayList;


public class DetalleMunicipioFragment extends Fragment {

    String destino = seleccionar_destino.destino;
    AdapterDetalleMunicipio adapterDetalleMunicipio;
    RecyclerView recyclerDetalleMunicipio;
    ConexionSQLiteDetalleMunicipioHelper conn;
    ArrayList<DetalleMunicipioVo> listaDetalleMunicipioVo;
    Spinner comboDetalleMunicipio;
    String TipoDetalleMunicipio;

    //Crear referencias para poder realizar la comunicacion entre el fragment detalle
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_municipio_fragment,container,false);
        try{

            conn = new   ConexionSQLiteDetalleMunicipioHelper(getContext(),"detalle",null,1);
            recyclerDetalleMunicipio = view.findViewById(R.id.recyclerViewDetalleMlist);



            comboDetalleMunicipio = (Spinner) view.findViewById(R.id.idSpinnerDetalleM);
            ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.comboDetalleM
            ,android.R.layout.simple_spinner_item);
            comboDetalleMunicipio.setAdapter(adapter);

            comboDetalleMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                    TipoDetalleMunicipio = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selecciono: "+TipoDetalleMunicipio,Toast.LENGTH_SHORT).show();
                    ConsultarDetalleM();
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

    public void ConsultarDetalleM(){
        SQLiteDatabase db = conn.getReadableDatabase();
        listaDetalleMunicipioVo = new ArrayList<>();
        DetalleMunicipioVo detalleMunicipioVo = null;
        String[] result = new String[] {destino,TipoDetalleMunicipio};
        Cursor cursor = db.rawQuery(" SELECT * FROM detalle WHERE municipio=? AND tipo=?", result);
        while(cursor.moveToNext()){
            detalleMunicipioVo = new DetalleMunicipioVo();
            detalleMunicipioVo.setCodigo(cursor.getInt(0));
            detalleMunicipioVo.setTipo(cursor.getString(1));
            detalleMunicipioVo.setMunicipio(cursor.getString(2));
            detalleMunicipioVo.setNombreDetalleM(cursor.getString(3));
            detalleMunicipioVo.setInfoGM(cursor.getString(4));
            detalleMunicipioVo.setDescripcionDetalleM(cursor.getString(5));
            detalleMunicipioVo.setFotoDetalleM(cursor.getInt(6));
            detalleMunicipioVo.setImageDetalleM(cursor.getInt(7));


            listaDetalleMunicipioVo.add(detalleMunicipioVo);
        }
        recyclerDetalleMunicipio.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterDetalleMunicipio = new AdapterDetalleMunicipio(getContext(),listaDetalleMunicipioVo);
        recyclerDetalleMunicipio.setAdapter(adapterDetalleMunicipio);

        adapterDetalleMunicipio.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó: "+  listaDetalleMunicipioVo.get( recyclerDetalleMunicipio.getChildAdapterPosition(view)).getNombreDetalleM(), Toast.LENGTH_SHORT).show();
               interfaceComunicaFragments.enviarDetalleMunicipio(listaDetalleMunicipioVo.get(recyclerDetalleMunicipio.getChildAdapterPosition(view)));
            }
        });
    }



}
