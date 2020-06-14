package com.example.cultucesar.Fragments.EventosCulturales;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cultucesar.Entidades.EventoVo;
import com.example.cultucesar.R;


public class DetalleEventoFragment extends Fragment {
    TextView nombreE,infoG,fechaE,valorE,telefonoE;
    ImageView imagen;
    Button btnLink;
    String linkEvento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_eventos_fragment,container,false);
        nombreE = view.findViewById(R.id.nombreEvento);
        imagen = view.findViewById(R.id.imagen_eventoDetalle);
        infoG = view.findViewById(R.id.InfoG_Evento);
        fechaE = view.findViewById(R.id.fechaEvento);
        valorE =view.findViewById(R.id.valorEvento);
        telefonoE =view.findViewById(R.id.telefonoEvento);
        btnLink = view.findViewById(R.id.btnLinkEvent);



        Bundle objetoEventos = getArguments();
        EventoVo eventoVo = null;
        if(objetoEventos !=null){
            eventoVo = (EventoVo) objetoEventos.getSerializable("objeto");
            nombreE.setText(eventoVo.getNombreEvento());
            imagen.setImageResource(eventoVo.getImagen());
            infoG.setText(eventoVo.getInfoGeneral());
            fechaE.setText(eventoVo.getFechaEvento());
            valorE.setText(eventoVo.getValorEvento());
            telefonoE.setText(eventoVo.getTelefonoEvento());
            linkEvento = eventoVo.getSitioWeb();
        }

        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(linkEvento);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        return view;
    }


}
