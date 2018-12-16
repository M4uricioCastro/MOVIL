package com.example.maury.saacloop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maury.saacloop.saac.Actividad;

import java.util.List;

public class AdaptadorTareas extends RecyclerView.Adapter<AdaptadorTareas.tareaHolder>{
private Activity activity;
private int recurso;
private List<Actividad> actividadList;

    public AdaptadorTareas(Activity activity, int recurso, List<Actividad> actividadList) {
        this.activity = activity;
        this.recurso = recurso;
        this.actividadList = actividadList;
    }

    @NonNull
    @Override
    public tareaHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(recurso,parent,false);
        return new tareaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tareaHolder tareaHolder, int i) {
        Actividad a = actividadList.get(i);
        tareaHolder.txtoracion.setText(a.Oracion);
        tareaHolder.txtestado.setText(a.Estado);
        tareaHolder.txtactividad.setText(a.idActividad+"");
        tareaHolder.id = a.idActividad+"";
    }

    @Override
    public int getItemCount() {
        return actividadList.size();
    }
    public class tareaHolder extends RecyclerView.ViewHolder{
        private TextView txtoracion,txtestado,txtactividad;
        private String id;
        public tareaHolder(@NonNull View itemView) {
            super(itemView);
            txtoracion = itemView.findViewById(R.id.item_oracion);
            txtestado = itemView.findViewById(R.id.item_estado);
            txtactividad = itemView.findViewById(R.id.item_idActividad);
            txtoracion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, TareasActivity.class);
                    SharedPreferences prefs =
                            activity.getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("idActividad", id);
                    editor.commit();
                    activity.startActivity(i);
                }
            });
        }
    }
}
