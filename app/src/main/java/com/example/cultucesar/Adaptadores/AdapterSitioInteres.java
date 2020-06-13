package com.example.cultucesar.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultucesar.Entidades.EventoVo;
import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.R;

import java.util.ArrayList;

public class AdapterSitioInteres extends RecyclerView.Adapter<AdapterSitioInteres.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<SitioInteresVo> sitioInteresVo;
    private View.OnClickListener listener;


    public AdapterSitioInteres(ArrayList<SitioInteresVo> sitioInteresVo,Context context) {
        this.sitioInteresVo = sitioInteresVo;
        this.inflater = LayoutInflater.from(context);
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
    public AdapterSitioInteres.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.lista_sitios_interes,parent,false);
       view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSitioInteres.ViewHolder holder, int position) {
        int imagenSitioInteres = sitioInteresVo.get(position).getFotoSitioInteres();
        String nombreSitioInteres = sitioInteresVo.get(position).getNombreSitioInteres();
        String valorSitiInteres = sitioInteresVo.get(position).getValorSitioInteres();

        holder.imagenSitioI.setImageResource(imagenSitioInteres);
        holder.nombreSitioI.setText(nombreSitioInteres);
        holder.valorSitioI.setText(valorSitiInteres);


    }

    @Override
    public int getItemCount() {
        return sitioInteresVo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreSitioI,valorSitioI;
        ImageView imagenSitioI;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenSitioI = itemView.findViewById(R.id.imagen_sitioInteres);
            nombreSitioI = itemView.findViewById(R.id.nombreSitioInteres);
            valorSitioI = itemView.findViewById(R.id.valorSitioInteres);



        }
    }
}
