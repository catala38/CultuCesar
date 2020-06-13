package com.example.cultucesar.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultucesar.Adaptadores.AdapterDestinos;
import com.example.cultucesar.Entidades.DestinosVo;
import com.example.cultucesar.Interfaces.iComunicaFragments;
import com.example.cultucesar.R;

import java.util.ArrayList;

public class DestinosFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;


    AdapterDestinos adapterPersonas;
    RecyclerView recyclerViewPersonas;
    ArrayList<DestinosVo> listaDestinos;

    EditText txtnombre;

    //Crear referencias para poder realizar la comunicacion entre el fragment detalle
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.evento_fragment,container,false);
       // txtnombre = view.findViewById(R.id.txtnombre);

       // recyclerViewPersonas = view.findViewById(R.id.recyclerView);
        listaDestinos = new ArrayList<>();
        cargarLista();
        mostrarData();
        return view;
    }


    public void cargarLista(){
        listaDestinos.add(new DestinosVo("Valledupar","Ciudad de los santos reyes",R.drawable.valledupar,"A orillas del Río Guatapurí se encuentra esta ciudad fundada en 1550 por Hernando de Santana y epicentro de uno de los géneros musicales más tradicionales de Colombia: el Vallenato. A ritmo de caja, guacharaca y acordeón podrá visitar sus lugares icónicos como la renovada Plaza Alfonso López, en el centro histórico, el Parque de la Leyenda Vallenata y el Museo del Acordeón, que le permitirán acercarse a algunos de los elementos más representativos de la cultura vallenata. Valledupar es una ciudad acogedora y tranquila, sus habitantes son amables y alegres.","prueba"));
        listaDestinos.add(new DestinosVo("Manaure","Balcon del cesar",R.drawable.manaure1,"A 32 kilómetros de Valledupar, se encuentra este encanto cesarense, rico en producción agrícola, especialmente frutas, cacao, plantas medicinales y heliconias. Un entorno natural demarcado por hermosas montañas pinceladas por diversos verdes, quebradas, una cascada, un río y la temperatura promedio de 24 °C, hacen de sus 1.700 kms., un espacio para el disfrute, la alegría y la felicidad.","prueba"));
    }


    private void mostrarData(){
        recyclerViewPersonas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPersonas = new AdapterDestinos(getContext(), listaDestinos);
        recyclerViewPersonas.setAdapter(adapterPersonas);

        adapterPersonas.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String nombre = listaDestinos.get(recyclerViewPersonas.getChildAdapterPosition(view)).getNombre();
               txtnombre.setText(nombre);
               Toast.makeText(getContext(), "Seleccionó: "+ listaDestinos.get(recyclerViewPersonas.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();
                //enviar mediante la interface el objeto seleccionado al detalle
                //se envia el objeto completo
                //se utiliza la interface como puente para enviar el objeto seleccionado
                interfaceComunicaFragments.enviarDestinos(listaDestinos.get(recyclerViewPersonas.getChildAdapterPosition(view)));
                //luego en el mainactivity se hace la implementacion de la interface para implementar el metodo enviarpersona
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
        //mListener = null;
    }

    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
