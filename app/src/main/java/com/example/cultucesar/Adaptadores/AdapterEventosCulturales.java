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
import com.example.cultucesar.R;

import java.util.ArrayList;

public class AdapterEventosCulturales extends RecyclerView.Adapter<AdapterEventosCulturales.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<EventoVo> eventoVo;
    private View.OnClickListener listener;

    public AdapterEventosCulturales(Context context, ArrayList<EventoVo> eventoVo) {
     this.inflater = LayoutInflater.from(context);
     this.eventoVo = eventoVo;
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
    public AdapterEventosCulturales.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_eventos, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEventosCulturales.ViewHolder holder, int position) {
        int imagen = eventoVo.get(position).getImagen();
        String nombre = eventoVo.get(position).getNombreEvento();
        String fecha = eventoVo.get(position).getFechaEvento();

        holder.imagen.setImageResource(imagen);
        holder.nombre.setText(nombre);
        holder.fecha.setText(fecha);

    }

    @Override
    public int getItemCount() {
        return eventoVo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView nombre,fecha;
       ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen =itemView.findViewById(R.id.imagen_evento);
            nombre = itemView.findViewById(R.id.nombreEvento);
            fecha = itemView.findViewById(R.id.fechaEvento);


        }
    }
}
