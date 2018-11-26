package com.example.maury.saacloop;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maury.saacloop.saac.Curso;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.cursoHolder>{
    private Activity activity;
    private int recurso;
    private List<Curso> cursoList;

    public Adaptador(Activity activity, int recurso, List<Curso> cursoList) {
        this.activity = activity;
        this.recurso = recurso;
        this.cursoList = cursoList;
    }

    @NonNull
    @Override
    public cursoHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(recurso,parent,false);
        return new cursoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cursoHolder cursoHolder, int i) {
        Curso c = cursoList.get(i);
        cursoHolder.txtcircle.setText(c.Nombre.charAt(0)+"");
        cursoHolder.txtnombre.setText(c.Nombre);
        cursoHolder.id = c.idCurso+"";
    }

    @Override
    public int getItemCount() {
        return cursoList.size();
    }

    public class cursoHolder extends RecyclerView.ViewHolder{
        private TextView txtcircle, txtnombre;
        private String id;
        public cursoHolder(@NonNull View itemView) {
            super(itemView);
            txtcircle = itemView.findViewById(R.id.item_circleCurso);
            txtnombre = itemView.findViewById(R.id.item_nombreCurso);
            txtnombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity,"id"+id,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}