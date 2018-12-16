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

import com.example.maury.saacloop.saac.Alumno;
import com.example.maury.saacloop.saac.Curso;

import java.util.List;

public class AdaptadorAlumno extends RecyclerView.Adapter<AdaptadorAlumno.alumnoHolder>{
    private Activity activity;
    private int recurso;
    private List<Alumno> alumnoList;

    public AdaptadorAlumno(Activity activity, int recurso, List<Alumno> cursoList) {
        this.activity = activity;
        this.recurso = recurso;
        this.alumnoList = cursoList;
    }

    @NonNull
    @Override
    public alumnoHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(recurso,parent,false);
        return new alumnoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull alumnoHolder alumnoHolder, int i) {
        Alumno c = alumnoList.get(i);
        alumnoHolder.txtcircle.setText(c.Nombre.charAt(0)+"");
        alumnoHolder.txtnombre.setText(c.Nombre);
        alumnoHolder.txtIdcurso.setText(c.idCurso+"");
        alumnoHolder.txtRut.setText(c.Rut+"");
        alumnoHolder.id = c.idCurso+"";
        alumnoHolder.rut = c.Rut+"";
    }

    @Override
    public int getItemCount() {
        return alumnoList.size();
    }

    public class alumnoHolder extends RecyclerView.ViewHolder{
        private TextView txtcircle, txtnombre, txtIdcurso,txtRut;
        private String id,rut;
        public alumnoHolder(@NonNull View itemView) {
            super(itemView);
            txtcircle = itemView.findViewById(R.id.item_circleAlumno);
            txtnombre = itemView.findViewById(R.id.item_nombreAlumno);
            txtRut = itemView.findViewById(R.id.item_rutAlumno);
            txtIdcurso = itemView.findViewById(R.id.item_idCurso);
           txtnombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, menuActivity.class);
                    i.putExtra("ID",id);
                    i.putExtra("RUT",rut);
                    SharedPreferences prefs =
                            activity.getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("rutAlumno", rut);
                    editor.commit();
                    activity.startActivity(i);
                }
            });
        }
    }
}
