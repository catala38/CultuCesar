package com.example.cultucesar.Fragments.Actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cultucesar.Entidades.ActividadesVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;
import com.example.cultucesar.R;


public class DetalleActividadesFragment extends Fragment {
    TextView nombreActividad,infoGActividad;
    Button btnLinkActividad;
    String linkActividad;
    ViewFlipper viewFlipperActividad;
    int imagenes[];


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detalle_actividad_fragment,container,false);
        nombreActividad = view.findViewById(R.id.nombreActividad);
        infoGActividad = view.findViewById(R.id.InfoGActividad);
        btnLinkActividad = view.findViewById(R.id.btnLinkActividad);
        viewFlipperActividad = view.findViewById(R.id.flipperActividad);

        Bundle objetoActividad = getArguments();
        ActividadesVo actividadesVo = null;;
        if(objetoActividad !=null){
            actividadesVo = (ActividadesVo) objetoActividad.getSerializable("objeto");
            imagenes  = new int[]{Integer.parseInt(String.valueOf(actividadesVo.getImagenD())), Integer.parseInt(String.valueOf(actividadesVo.getImagenP()))};
            nombreActividad.setText(actividadesVo.getNombreActividad());
            infoGActividad.setText(actividadesVo.getInfoGeneral());
            linkActividad = actividadesVo.getDetalleActividad();
        }

        for(int imagen:imagenes){
            flipperImagenes(imagen);
        }

        btnLinkActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(linkActividad);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });


        return view;

    }

    public void flipperImagenes(int imagen){
        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setBackgroundResource(imagen);
        viewFlipperActividad.addView(imageView);
        viewFlipperActividad.setFlipInterval(3000);
        viewFlipperActividad.setAutoStart(true);
        viewFlipperActividad.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipperActividad.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }


}
