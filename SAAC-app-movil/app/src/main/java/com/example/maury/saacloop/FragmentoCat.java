package com.example.maury.saacloop;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maury.saacloop.saac.CrudCategoria;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoCat extends Fragment {
    CrudCategoria crudCategoria = menuActivity.crudCategoria;
    ListView lv;
    public FragmentoCat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] elementos = crudCategoria.categoriaList();
        lv = getView().findViewById(R.id.List_Cat);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_1,elementos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println(crudCategoria.categoriaList().size());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragmento_cat, container, false);
    }

}
