package com.example.maury.saacloop;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.maury.saacloop.saac.Pictograma;
import com.github.snowdream.android.widget.SmartImageView;

import java.util.List;

public class AdapatadorTareasOracion extends RecyclerView.Adapter<AdapatadorTareasOracion.TareasOracionHolder> {

    private Activity activity;
    private int recurso;
    private List<Pictograma> pictogramaList;

    public AdapatadorTareasOracion(Activity activity, int recurso, List<Pictograma> pictogramaList) {
        this.activity = activity;
        this.recurso = recurso;
        this.pictogramaList = pictogramaList;

    }

    @NonNull
    @Override
    public TareasOracionHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(recurso,parent,false);
        return new TareasOracionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasOracionHolder TareasOracionHolder, int i) {
        Pictograma p = pictogramaList.get(i);
        String urlfinal = "http://170.239.85.176/"+p.img;
        TareasOracionHolder.smartImageView.setImageUrl(urlfinal ,TareasOracionHolder.rect);

    }

    @Override
    public int getItemCount() {
        return pictogramaList.size();
    }
    public class TareasOracionHolder extends RecyclerView.ViewHolder{
        SmartImageView smartImageView;
        Rect rect;

        public TareasOracionHolder(@NonNull View itemView) {
            super(itemView);
            smartImageView = itemView.findViewById(R.id.item_imagenp1);
            rect = new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
        }
    }

}
