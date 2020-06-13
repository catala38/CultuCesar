package com.example.cultucesar.Fragments.EventosCulturales;
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

import com.example.cultucesar.Adaptadores.AdapterEventosCulturales;
import com.example.cultucesar.Data.ConexionSQLiteEventoHelper;
import com.example.cultucesar.Entidades.EventoVo;
import com.example.cultucesar.R;
import com.example.cultucesar.seleccionar_destino;

import java.util.ArrayList;


public class EventosFragment extends Fragment {

    String destino = seleccionar_destino.destino;
    AdapterEventosCulturales adapterEvento;
    RecyclerView recyclerEvento;
    ConexionSQLiteEventoHelper conn;
    ArrayList<EventoVo> listaEventoVo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.evento_fragment,container,false);
        try{

            conn = new ConexionSQLiteEventoHelper(getContext(),"eventos",null,1);
            recyclerEvento = view.findViewById(R.id.recyclerViewEvento);
            ConsultarEventos();



        }catch (Exception e){

        }

        return view;
    }

    public void ConsultarEventos(){
        SQLiteDatabase db = conn.getReadableDatabase();
        listaEventoVo = new ArrayList<>();
        EventoVo eventoVo= null;
        String[] result = new String[] {destino};
        Cursor cursor = db.rawQuery(" SELECT * FROM eventos WHERE municipio=? ", result);

        while(cursor.moveToNext()){
            eventoVo = new EventoVo();
            eventoVo.setCodigo(cursor.getInt(0));
            eventoVo.setMunicipio(cursor.getString(1));
            eventoVo.setNombreEvento(cursor.getString(2));
            eventoVo.setInfoGeneral(cursor.getString(3));
            eventoVo.setFechaEvento(cursor.getString(4));
            eventoVo.setValorEvento(cursor.getString(5));
            eventoVo.setTelefonoEvento(cursor.getString(6));
            eventoVo.setSitioWeb(cursor.getString(7));
            eventoVo.setImagen(cursor.getInt(8));
            listaEventoVo.add(eventoVo);
        }
        recyclerEvento.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterEvento = new AdapterEventosCulturales(getContext(), listaEventoVo);
        recyclerEvento.setAdapter(adapterEvento);

        adapterEvento.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Seleccionó: "+ listaEventoVo.get(recyclerEvento.getChildAdapterPosition(view)).getNombreEvento(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
