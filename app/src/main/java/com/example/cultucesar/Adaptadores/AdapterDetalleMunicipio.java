package com.example.cultucesar.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultucesar.Entidades.DetalleMunicipioVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;
import com.example.cultucesar.R;

import java.util.ArrayList;

public class AdapterDetalleMunicipio extends RecyclerView.Adapter<AdapterDetalleMunicipio.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<DetalleMunicipioVo> detalleMunicipioVo;
    private View.OnClickListener listener;


    public AdapterDetalleMunicipio(Context context, ArrayList<DetalleMunicipioVo> detalleMunicipioVo) {
        this.inflater = LayoutInflater.from(context);
        this.detalleMunicipioVo = detalleMunicipioVo;

    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterDetalleMunicipio.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.lista_detalle_municipio,parent,false);
       view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetalleMunicipio.ViewHolder holder, int position) {
        int imagenDetalleM = detalleMunicipioVo.get(position).getFotoDetalleM();
        String nombreDetalleM = detalleMunicipioVo.get(position).getNombreDetalleM();

        holder.imagenDetalleM.setImageResource(imagenDetalleM);
        holder.nombreDetalleM.setText(nombreDetalleM);

    }

    @Override
    public int getItemCount() {
        return detalleMunicipioVo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreDetalleM;
        ImageView imagenDetalleM;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenDetalleM = itemView.findViewById(R.id.imagen_detalleMlist);
            nombreDetalleM = itemView.findViewById(R.id.nombredetalleMlist);

        }
    }
}
