package com.example.maury.saacloop;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maury.saacloop.saac.Actividad;
import com.example.maury.saacloop.saac.CrudActividad;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoTareas extends Fragment {
    private RecyclerView recycler;
    private TextView txtoracion, txtestado,txtactividad;
    //CrudActividad crudActividad = menuActivity.crudActividad;

    public FragmentoTareas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragmento_tareas, container, false);

    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        txtoracion = getView().findViewById(R.id.item_oracion);
        txtestado = getView().findViewById(R.id.item_estado);
        txtactividad = getView().findViewById(R.id.item_idActividad);
        recycler = getView().findViewById(R.id.recyclerT);
        cargaTareas();
        super.onActivityCreated(savedInstanceState);
    }
    public void cargaTareas(){
        LinearLayoutManager lm = new LinearLayoutManager(getActivity().getApplicationContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(lm);
        try{
            SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
            int idShar = prefs.getInt("idCurso",-1);
            CrudActividad crudActividad = new CrudActividad(getActivity().getApplicationContext());
            List<Actividad> actividadList = crudActividad.actividadList(idShar);
            Log.e("fragmentoActividad",actividadList.size()+"");
            AdaptadorTareas ad = new AdaptadorTareas(getActivity(),R.layout.item_tareas,actividadList);
            recycler.setAdapter(ad);
            Log.e("Fragmento tareas AD","FragT");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
