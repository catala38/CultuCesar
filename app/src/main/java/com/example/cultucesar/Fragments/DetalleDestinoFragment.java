package com.example.cultucesar.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cultucesar.Entidades.DestinosVo;
import com.example.cultucesar.R;

public class DetalleDestinoFragment extends Fragment {
    TextView nombre,infoG;
    ViewFlipper viewFlipper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      int imagenes[]={Integer.parseInt(String.valueOf(R.drawable.valledupar1)),Integer.parseInt(String.valueOf(R.drawable.valledupar2)),Integer.parseInt(String.valueOf(R.drawable.valledupar3))};



        View view = inflater.inflate(R.layout.destino_valledupar_fragment,container,false);
        nombre = view.findViewById(R.id.nombre_detalle);
        infoG = view.findViewById(R.id.infoG);

        //Crear bundle para recibir el objeto enviado por parametro.
        Bundle objetoPersona = getArguments();
        DestinosVo destinosVo = null;;
        //validacion para verificar si existen argumentos para mostrar
        if(objetoPersona !=null){
            destinosVo = (DestinosVo) objetoPersona.getSerializable("objeto");
            nombre.setText(destinosVo.getNombre());
            infoG.setText(destinosVo.getInfoG());

        }

        //Slider de imagenes
        viewFlipper = view.findViewById(R.id.flipper);
        for(int imagen:imagenes){
            flipperImagenes(imagen);
        }



        return view;
    }

    public void flipperImagenes(int imagen){
        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setBackgroundResource(imagen);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }
}
