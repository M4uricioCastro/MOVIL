package com.example.maury.saacloop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maury.saacloop.saac.Pictograma;
import com.github.snowdream.android.widget.SmartImageView;

import java.util.List;

public class AdaptadorTareasAlternativas extends RecyclerView.Adapter<AdaptadorTareasAlternativas.TareasAlternativaHolder>  {

    private Activity activity;
    private int recurso;
    private List<Pictograma> pictogramaList;

    public AdaptadorTareasAlternativas(Activity activity, int recurso, List<Pictograma> pictogramaList) {
        this.activity = activity;
        this.recurso = recurso;
        this.pictogramaList = pictogramaList;

    }

    @NonNull
    @Override
    public TareasAlternativaHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(recurso,parent,false);
        return new TareasAlternativaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasAlternativaHolder TareasAlternativaHolder, int i) {
        Pictograma p = pictogramaList.get(i);
        String urlfinal = "http://170.239.85.176/"+p.img;
        TareasAlternativaHolder.smartImageView.setImageUrl(urlfinal ,TareasAlternativaHolder.rect);
        TareasAlternativaHolder.id = p.idPictograma+"";

    }

    @Override
    public int getItemCount() {
        return pictogramaList.size();
    }
    public class TareasAlternativaHolder extends RecyclerView.ViewHolder{
        SmartImageView smartImageView;
        Rect rect;
        String id;
        long startTime;
        long endTime;
        public TareasAlternativaHolder(@NonNull View itemView) {
            super(itemView);
            startTime = System.currentTimeMillis();
            smartImageView = itemView.findViewById(R.id.item_imagenp1);
            rect = new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
            smartImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, menuActivity.class);
                    SharedPreferences prefs =
                            activity.getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("idPictograma", id);
                    endTime = System.currentTimeMillis() - startTime;
                    editor.putString("Tiempo",endTime+"");
                    editor.commit();
                    activity.startActivity(i);
                }
            });
        }
    }
}
