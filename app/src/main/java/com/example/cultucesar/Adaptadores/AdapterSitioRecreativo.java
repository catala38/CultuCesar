package com.example.cultucesar.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.Entidades.SitioRecreativoVo;
import com.example.cultucesar.R;

import java.util.ArrayList;

public class AdapterSitioRecreativo extends RecyclerView.Adapter<AdapterSitioRecreativo.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<SitioRecreativoVo> sitioRecreativoVo;
    private View.OnClickListener listener;


    public AdapterSitioRecreativo(Context context,ArrayList<SitioRecreativoVo> sitioRecreativoVo) {
        this.inflater = LayoutInflater.from(context);
        this.sitioRecreativoVo = sitioRecreativoVo;

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
    public AdapterSitioRecreativo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.lista_sitios_recreativo,parent,false);
       view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSitioRecreativo.ViewHolder holder, int position) {
        int imagenSitioRecreativo = sitioRecreativoVo.get(position).getImageDetalleS1Recreativo();
        String nombreSitioRecreativo = sitioRecreativoVo.get(position).getNombreSitioRecreativo();

        holder.imagenSitioR.setImageResource(imagenSitioRecreativo);
        holder.nombreSitioR.setText(nombreSitioRecreativo);

    }

    @Override
    public int getItemCount() {
        return sitioRecreativoVo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreSitioR;
        ImageView imagenSitioR;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenSitioR = itemView.findViewById(R.id.imagen_sitioRecreativo);
            nombreSitioR = itemView.findViewById(R.id.nombreSitioRecreativo);

        }
    }
}
