package com.example.cultucesar.Fragments.SitioRecreativo;

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
import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;
import com.example.cultucesar.R;


public class DetalleSitioRecreativoFragment extends Fragment {

    TextView nombreSitioR,infoGSitioR;
    Button btnLinkSitioR;
    String linkSitioR;
    ViewFlipper viewFlipperSitioRecreativo;
    int imagenes[];


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_sitio_recreativo_fragment,container,false);
        nombreSitioR = view.findViewById(R.id.nombreSitioR);
        infoGSitioR = view.findViewById(R.id.InfoGSitioR);
        btnLinkSitioR = view.findViewById(R.id.btnLinkSitioR);
        viewFlipperSitioRecreativo = view.findViewById(R.id.flipperSitioR);

        Bundle objetoSitioR = getArguments();
        SitioRecreativoVo sitioRecreativoVo = null;;
        if(objetoSitioR !=null){
            sitioRecreativoVo = (SitioRecreativoVo) objetoSitioR.getSerializable("objeto");
            imagenes  = new int[]{Integer.parseInt(String.valueOf(sitioRecreativoVo.getImageDetalleS1Recreativo())), Integer.parseInt(String.valueOf(sitioRecreativoVo.getImageDetalleS2Recreativo())), Integer.parseInt(String.valueOf(sitioRecreativoVo.getImageDetalleS3Recreativo()))};
            nombreSitioR.setText(sitioRecreativoVo.getNombreSitioRecreativo());
            infoGSitioR.setText(sitioRecreativoVo.getInfoSitioRecreativo());
            linkSitioR = sitioRecreativoVo.getDetalleSitioRecreativo();
        }

        for(int imagen:imagenes){
            flipperImagenes(imagen);
        }

        btnLinkSitioR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(linkSitioR);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });


        return view;

    }

    public void flipperImagenes(int imagen){
        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setBackgroundResource(imagen);
        viewFlipperSitioRecreativo.addView(imageView);
        viewFlipperSitioRecreativo.setFlipInterval(3000);
        viewFlipperSitioRecreativo.setAutoStart(true);
        viewFlipperSitioRecreativo.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipperSitioRecreativo.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }
}
