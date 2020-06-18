package com.example.cultucesar.Fragments.DetalleMunicipio;

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

import com.example.cultucesar.Entidades.DetalleMunicipioVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;
import com.example.cultucesar.R;


public class DetalleDetalleMunicipioFragment extends Fragment {

    TextView nombreDetalleM,infoGDetalleM;
    Button btnLinkDetalleM;
    String linkDetalleM;
    ViewFlipper viewFlipperDetalleM;
    int imagenes[];


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_detalle_municipio_fragment,container,false);
        nombreDetalleM = view.findViewById(R.id.nombreDetalleM);
        infoGDetalleM = view.findViewById(R.id.InfoGDetalleM);
        btnLinkDetalleM = view.findViewById(R.id.btnLinkDetalleM);
        viewFlipperDetalleM = view.findViewById(R.id.flipperDetalleM);

        Bundle objetoSitioR = getArguments();
        DetalleMunicipioVo detalleMunicipioVo = null;
        if(objetoSitioR !=null){
            detalleMunicipioVo = (DetalleMunicipioVo) objetoSitioR.getSerializable("objeto");
            imagenes  = new int[]{Integer.parseInt(String.valueOf(detalleMunicipioVo.getFotoDetalleM())), Integer.parseInt(String.valueOf(detalleMunicipioVo.getImageDetalleM()))};
            nombreDetalleM.setText(detalleMunicipioVo.getNombreDetalleM());
            infoGDetalleM.setText(detalleMunicipioVo.getInfoGM());
            linkDetalleM = detalleMunicipioVo.getDescripcionDetalleM();
        }

        for(int imagen:imagenes){
            flipperImagenes(imagen);
        }

        btnLinkDetalleM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(linkDetalleM);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });


        return view;


    }

    public void flipperImagenes(int imagen){
        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setBackgroundResource(imagen);
        viewFlipperDetalleM.addView(imageView);
        viewFlipperDetalleM.setFlipInterval(3000);
        viewFlipperDetalleM.setAutoStart(true);
        viewFlipperDetalleM.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipperDetalleM.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }


}