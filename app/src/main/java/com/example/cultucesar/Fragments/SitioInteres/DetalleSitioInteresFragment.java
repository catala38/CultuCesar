package com.example.cultucesar.Fragments.SitioInteres;

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

import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.R;


public class DetalleSitioInteresFragment extends Fragment {
    TextView nombreSitioI,infoGSitioI,valorSitioI,telefonoSitioI;
    ImageView imagenSitioI1,imagenSitioI2,imagenSitioI3;
    Button btnLinkSitipI;
    String linkSitioI;
    ViewFlipper viewFlipperSitioInteres;
    int imagenes[];


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_sitio_interes_fragment,container,false);
        nombreSitioI = view.findViewById(R.id.nombreSitioInteres);
        infoGSitioI = view.findViewById(R.id.InfoG_SitioI);
        valorSitioI = view.findViewById(R.id.valorSitioI);
        telefonoSitioI = view.findViewById(R.id.telefonoSitioI);
        btnLinkSitipI = view.findViewById(R.id.btnLinkSitioI);
        viewFlipperSitioInteres = view.findViewById(R.id.flipperSitioInteres);

        Bundle objetoSitioI = getArguments();
        SitioInteresVo sitioInteresVo = null;;
        if(objetoSitioI !=null){
            sitioInteresVo = (SitioInteresVo) objetoSitioI.getSerializable("objeto");
             imagenes  = new int[]{Integer.parseInt(String.valueOf(sitioInteresVo.getImageDetalleS1Interes())), Integer.parseInt(String.valueOf(sitioInteresVo.getImageDetalleS2Interes())), Integer.parseInt(String.valueOf(sitioInteresVo.getImageDetalleS3Interes()))};
             nombreSitioI.setText(sitioInteresVo.getNombreSitioInteres());
             infoGSitioI.setText(sitioInteresVo.getInfoSitioInteres());
             valorSitioI.setText(sitioInteresVo.getValorSitioInteres());
             telefonoSitioI.setText(sitioInteresVo.getTelefonoSitioInteres());
             linkSitioI = sitioInteresVo.getSitioWebInteres();


        }

        for(int imagen:imagenes){
            flipperImagenes(imagen);
        }

        btnLinkSitipI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(linkSitioI);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });


        return view;

    }

    public void flipperImagenes(int imagen){
        ImageView imageView = new ImageView(getActivity().getApplicationContext());
        imageView.setBackgroundResource(imagen);
        viewFlipperSitioInteres.addView(imageView);
        viewFlipperSitioInteres.setFlipInterval(3000);
        viewFlipperSitioInteres.setAutoStart(true);
        viewFlipperSitioInteres.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipperSitioInteres.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }

}
