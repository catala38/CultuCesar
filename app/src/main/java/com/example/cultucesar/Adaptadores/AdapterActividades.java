package com.example.cultucesar.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultucesar.Entidades.ActividadesVo;
import com.example.cultucesar.Entidades.SitioInteresVo;
import com.example.cultucesar.R;

import java.util.ArrayList;

public class AdapterActividades extends RecyclerView.Adapter<AdapterActividades.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<ActividadesVo> actividadesVo;
    private View.OnClickListener listener;


    public AdapterActividades(ArrayList<ActividadesVo> ActividadesVo, Context context) {
        this.actividadesVo = ActividadesVo;
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
    public AdapterActividades.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.lista_actividad,parent,false);
       view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterActividades.ViewHolder holder, int position) {
        int imagenActividades = actividadesVo.get(position).getImagenP();
        String nombreActividades = actividadesVo.get(position).getNombreActividad();

        holder.imagenActividad.setImageResource(imagenActividades);
        holder.nombreActividad.setText(nombreActividades);

    }

    @Override
    public int getItemCount() {
        return actividadesVo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreActividad;
        ImageView imagenActividad;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenActividad = itemView.findViewById(R.id.imagen_Actividad);
            nombreActividad = itemView.findViewById(R.id.nombreActividad);

        }
    }
}
