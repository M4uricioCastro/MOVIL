package com.example.maury.saacloop;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.maury.saacloop.saac.Alumno;
import com.example.maury.saacloop.saac.CrudAlumno;
import com.example.maury.saacloop.saac.CrudCurso;
import com.example.maury.saacloop.saac.conexionHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class alumnoActivity extends AppCompatActivity {
    int id;
    private String ip="192.168.0.4";
    private TextView txtnombre, txtidCurso,txtrut;
    private RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        recycler = findViewById(R.id.recycler);
        txtnombre = findViewById(R.id.item_nombreAlumno);
        txtidCurso = findViewById(R.id.item_idCurso);
        txtrut = findViewById(R.id.item_rutAlumno);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("ID"));
        Log.e("AlumnoActivity",id+"");
        cargaItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargaItem();
    }
    public void cargaItem(){
        //cargar el item correspondiente
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(lm);
        //configuracion del adapatador
        try {
            CrudAlumno crudAlumno = new CrudAlumno(this);
            List<Alumno> ListaAlumno = crudAlumno.AlumnoList();
            AdaptadorAlumno ad = new AdaptadorAlumno(this,R.layout.item_alumno,ListaAlumno);
            recycler.setAdapter(ad);
            Log.e("Dentro","llego aqui!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
