package com.example.maury.saacloop;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maury.saacloop.saac.Categoria;
import com.example.maury.saacloop.saac.CrudCategoria;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoCat extends Fragment {
    CrudCategoria crudCategoria = menuActivity.crudCategoria;
    ListView lv;
    Intent i;
    public FragmentoCat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        System.out.println(crudCategoria.categoriaList().size());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragmento_cat, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = getView().findViewById(R.id.List_Cat);
        cargaListaCat();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //mandar a otra activity donde se veran las imagenes de las categoria

               i = new Intent(getActivity(),ImagenesActivity.class);
               i.putExtra("cod",position+"");
               startActivity(i);
            }
        });
    }
    private void cargaListaCat(){
        lv.setAdapter(new ArrayAdapter<Categoria>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,crudCategoria.categoriaList()));
    }
}
