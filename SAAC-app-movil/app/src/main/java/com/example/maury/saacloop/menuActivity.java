package com.example.maury.saacloop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.maury.saacloop.saac.Categoria;
import com.example.maury.saacloop.saac.CrudCategoria;
import com.example.maury.saacloop.saac.CrudPictograma;
import com.example.maury.saacloop.saac.Pictograma;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class menuActivity extends AppCompatActivity {
    int id;
    String Rut;
    private String ip="192.168.0.9";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("ID"));
        Rut =  intent.getStringExtra("RUT");
        Log.e("Menu", id+" Rut: "+Rut);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void categoria(){
        String url = "http://"+ip+"/SAAC-app-web/index.php/api/categorias";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    String respuesta = new String(responseBody);
                    cargaCategoria(respuesta);
                    Log.e("BDOnline", respuesta);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("info", statusCode+"<---------------------------");
            }
        });
    }
    public void cargaCategoria(String respuesta){
        try{
            CrudCategoria crudCategoria = new CrudCategoria(this);
            JSONArray json = new JSONArray(respuesta);
            List<Categoria> categoriaList = crudCategoria.categoriaList();
            if (categoriaList.size() < json.length()){
                for (int i=0; i<json.length();i++){
                    Categoria c = new Categoria();
                    c.idCategoria = json.getJSONObject(i).getInt("idCategoria");
                    crudCategoria.delete(c.idCategoria);
                    c.nombre = json.getJSONObject(i).getString("Nombre");
                    crudCategoria.insert(c);
                    Log.e("info","-------------------------");
                    Log.e("info",c.idCategoria+"");
                    Log.e("info",c.nombre);
                    Log.e("info","--------------------------");
                    categoriaList.add(c);
                }
            }else{Log.e("MSN","No hay Datos nuevos");}
        }catch (Exception e){e.printStackTrace();}
    }
    public void pictograma(){
        String url = "http://"+ip+"/SAAC-app-web/index.php/api/pictogramas";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    String respuesta = new String(responseBody);
                    cargaPictogramas(respuesta);
                    Log.e("BDOnline", respuesta);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("info", statusCode+"<---------------------------");
            }
        });
    }
    public void cargaPictogramas(String respuesta){
        try{
        CrudPictograma crudPictograma = new CrudPictograma(this);
        JSONArray json = new JSONArray(respuesta);
        List<Pictograma> pictogramaList = crudPictograma.pictogramaList();
        if (pictogramaList.size() < json.length()){
            for (int i=0; i<json.length();i++){
                Pictograma p = new Pictograma();
                p.idPictograma = json.getJSONObject(i).getInt("idPictograma");
                crudPictograma.delete(p.idPictograma);
                p.nombre = json.getJSONObject(i).getString("Nombre");
                p.descripcion = json.getJSONObject(i).getString("Descripcion");
                p.ejemplo = json.getJSONObject(i).getString("Ejemplo");
                p.tags = json.getJSONObject(i).getString("Tags");
                p.img = json.getJSONObject(i).getString("img");
                p.estado = json.getJSONObject(i).getString("Estado");
                p.idCategoria = json.getJSONObject(i).getInt("idCategoria");
                p.RutDocente = json.getJSONObject(i).getInt("RutDocente");
                crudPictograma.insert(p);
                Log.e("info","-------------------------");
                Log.e("info",p.idPictograma+"");
                Log.e("info",p.nombre);
                Log.e("info","--------------------------");
                pictogramaList.add(p);
            }
        }else{Log.e("MSN","No hay Datos nuevos");}
    }catch (Exception e){e.printStackTrace();}
    }
}
