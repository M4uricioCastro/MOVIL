package com.example.maury.saacloop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.maury.saacloop.saac.CrudCurso;
import com.example.maury.saacloop.saac.Curso;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private TextView txtnombre, txtidCurso;
    private RecyclerView recycler ;
    private String ip="192.168.43.58";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recycler);
        txtnombre = findViewById(R.id.item_nombre);
        txtidCurso = findViewById(R.id.item_idCurso);
        //mascotas();
        CrudCurso crudCurso = new CrudCurso(this);
        Curso c = crudCurso.find(666);
        Log.e("info","-------------------------");
        Log.e("info",c.Nombre);
        Log.e("info","--------------------------");
    }


    @Override
    protected void onResume() {
        mascotas();
        super.onResume();
    }

    /*tres servicios php
        mascotas: entrega todas las mascotas
        findMascotasById: busca a una mascota por id
        insertMascota: inserta una mascota en la BD
        * */
    public void mascotas(){
        String url = "http://"+ip+"/SAAC-app-web/index.php/api/cursos";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    String respuesta = new String(responseBody);
                    cargaRecycler(respuesta);
                    Log.e("BDOnline", respuesta);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("info", statusCode+"<---------------------------");
                CargaOffline();
            }
        });
    }// end function
    public void buscarMascota(int id){
        String url = "http://raspberry.todojava.net/index.php/welcome/findMascotaById";
        RequestParams params = new RequestParams();
        params.put("id",id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    String respuesta = new String(responseBody);
                    Log.e("info", respuesta);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }// end function
    public void CargaOffline(){
        CrudCurso crudCurso = new CrudCurso(this);
        List<Curso> lista = crudCurso.cursoList();
        Adaptador ad = new Adaptador(this,R.layout.item_curso,lista);
        recycler.setAdapter(ad);
    }
    public void cargaRecycler(String respuesta){
        //cargar el item correspondiente
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(lm);

        //configuracion del adapatador
        try {
            //List<Mascota> lista = new ArrayList<>();
            List<Curso> cursoList = new ArrayList<>();
            JSONArray json = new JSONArray(respuesta);
            CrudCurso crudCurso = new CrudCurso(this);
            List<Curso> ListaCursosize = crudCurso.cursoList();
            if (ListaCursosize.size() < json.length()){
                cursoList.clear();
                for (int i=0; i<json.length();i++){
                    Curso c = new Curso();
                    //eliminar los repetidos
                    //ingresar todos
                    c.idCurso = json.getJSONObject(i).getInt("idCurso");
                    crudCurso.delete(c.idCurso);
                    c.Nombre = json.getJSONObject(i).getString("Nombre");
                    crudCurso.insert(c);

                    Log.e("info","-------------------------");
                    Log.e("info",c.idCurso+"");
                    Log.e("info",c.Nombre);
                    Log.e("info","--------------------------");
                    ListaCursosize.add(c);

                }
            }
            Adaptador ad = new Adaptador(this,R.layout.item_curso,ListaCursosize);
            recycler.setAdapter(ad);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void eventoInsertar(View view) {

        Intent i = new Intent(this,insertarActivity.class);
        startActivity(i);
    }
}
