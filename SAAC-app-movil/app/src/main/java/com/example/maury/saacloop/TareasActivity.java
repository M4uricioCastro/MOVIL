package com.example.maury.saacloop;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.maury.saacloop.saac.Actividad;
import com.example.maury.saacloop.saac.CrudActividad;
import com.example.maury.saacloop.saac.CrudPictograma;
import com.example.maury.saacloop.saac.Pictograma;

import java.util.ArrayList;
import java.util.List;

public class TareasActivity extends AppCompatActivity {
    private RecyclerView recyclerPreguntas ;
    private RecyclerView recyclerAlternativas;
    private TextView tvoracion;
    private String idShar;
    private String rutAlumno;
    private String picOracion;
    private int posRes;
    private List<Pictograma> picto;
    private List<Actividad> actividadList;
    private List<Pictograma> pictoAlternativa;
    private int cantidadAlternativas = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tareas);
        tvoracion = findViewById(R.id.item_oracion_actividad);
        recyclerPreguntas = findViewById(R.id.recyclerPregunta);
        recyclerAlternativas = findViewById(R.id.recyclerAlternativa);
        recyclerPreguntas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerAlternativas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        idShar = prefs.getString("idActividad","-1");
        rutAlumno = prefs.getString("rutAlumno","-1");
        picto = new ArrayList<>();
        actividadList = new ArrayList<>();
        pictoAlternativa = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargaOracion();
        cargaPicVista();
        cargaRecyclerPregunta();
        cargaPicRespuesta();
        cargaRecyclerAlternativas();
    }
    public void cargaOracion(){
        CrudActividad crudActividad = new CrudActividad(this);
        actividadList = crudActividad.actividadListID(Integer.parseInt(idShar));
        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        int idActividad ;
        for (int i=0;i<actividadList.size();i++){
            tvoracion.setText(actividadList.get(i).Oracion);
            picOracion = actividadList.get(i).PicsVista;
            posRes = actividadList.get(i).PosRespuesta;
            editor.putString("idActividadSale", actividadList.get(i).idActividad+"");
        }
        editor.commit();
    }
    public void cargaPicVista(){
        picOracion= picOracion.replace(",","");
        picOracion= picOracion.replace("{","");
        picOracion= picOracion.replace("}","");
        Log.e("largo oracion",picOracion.length()+"");
        Log.e("Oracion",picOracion);
        CrudPictograma crudPictograma = new CrudPictograma(this);
        for(int i =8;i<=picOracion.length()+1;i=i+8){
            String c = Character.toString(picOracion.charAt(i-1));
            Pictograma p = crudPictograma.find(Integer.parseInt(c));
            Log.e("picto",p.toString());
            picto.add(p);
        }
        Log.e("Lista Pictogramas",picto.size()+"");
    }
    public void cargaRecyclerPregunta(){
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerPreguntas.setLayoutManager(lm);
         AdapatadorTareasOracion ad = new AdapatadorTareasOracion(this,R.layout.item_actividad,picto);
        recyclerPreguntas.setAdapter(ad);
    }
    public void cargaPicRespuesta(){
        CrudPictograma crudPictograma = new CrudPictograma(this);
        CrudActividad crudActividad = new CrudActividad(this);
        int e=0;
        for (int i=0;i<actividadList.size();i++){
            Pictograma p = crudPictograma.find(actividadList.get(i).idPic1);
            pictoAlternativa.add(p);
            Pictograma a = crudPictograma.find(actividadList.get(i).idPic2);
            pictoAlternativa.add(a);
            Pictograma s = crudPictograma.find(actividadList.get(i).idPic3);
            pictoAlternativa.add(s);
            Pictograma d = crudPictograma.find(actividadList.get(i).idPic4);
            pictoAlternativa.add(d);
            switch (posRes){
                case 1:
                    e = p.idPictograma;
                    break;
                case 2:
                    e = a.idPictograma;
                    break;
                case 3:
                    e= s.idPictograma;
                    break;
                case 4:
                    e = d.idPictograma;
                    break;
            }
            }
        crudActividad.updatePoss(e,Integer.parseInt(idShar));

    }
    public void cargaRecyclerAlternativas(){
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerAlternativas.setLayoutManager(lm);
        AdaptadorTareasAlternativas ad = new AdaptadorTareasAlternativas(this,R.layout.item_actividad,pictoAlternativa);
        recyclerAlternativas.setAdapter(ad);
    }


}
