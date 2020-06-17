package com.example.cultucesar.Fragments.Actividades;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultucesar.Adaptadores.AdapterActividades;
import com.example.cultucesar.Data.ConexionSQLiteActividadHelper;
import com.example.cultucesar.Entidades.ActividadesVo;
import com.example.cultucesar.Interfaces.iComunicaFragments;
import com.example.cultucesar.R;
import com.example.cultucesar.seleccionar_destino;

import java.util.ArrayList;


public class ActiviadadesFragment extends Fragment {

    String destino = seleccionar_destino.destino;
    AdapterActividades adapterActividades;
    RecyclerView recyclerActividades;
    ConexionSQLiteActividadHelper conn;
    ArrayList<ActividadesVo> listaActividadesVo;

    //Crear referencias para poder realizar la comunicacion entre el fragment detalle
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actividad_fragment,container,false);


            conn = new ConexionSQLiteActividadHelper(getContext(),"actividades",null,1);
            recyclerActividades = view.findViewById(R.id.recyclerViewActividad);
            ConsultarActividades();


        return view;
    }

    public void ConsultarActividades(){
        SQLiteDatabase db = conn.getReadableDatabase();
        listaActividadesVo = new ArrayList<>();
        ActividadesVo actividadesVo = null;
        String[] result = new String[] {destino};
        Cursor cursor = db.rawQuery(" SELECT * FROM actividades WHERE municipio=? ", result);
        while(cursor.moveToNext()){
            actividadesVo = new ActividadesVo();
            actividadesVo.setCodigo(cursor.getInt(0));
            actividadesVo.setMunicipio(cursor.getString(1));
            actividadesVo.setNombreActividad(cursor.getString(2));
            actividadesVo.setInfoGeneral(cursor.getString(3));
            actividadesVo.setDetalleActividad(cursor.getString(4));
            actividadesVo.setImagenD(cursor.getInt(5));
            actividadesVo.setImagenP(cursor.getInt(6));
            listaActividadesVo.add(actividadesVo);
        }

        recyclerActividades.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterActividades = new AdapterActividades(getContext(), listaActividadesVo);
        recyclerActividades.setAdapter(adapterActividades);

        adapterActividades.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó: "+ listaActividadesVo.get(recyclerActividades.getChildAdapterPosition(view)).getNombreActividad(), Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarActividades(listaActividadesVo.get(recyclerActividades.getChildAdapterPosition(view)));

            }
        });
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

}
