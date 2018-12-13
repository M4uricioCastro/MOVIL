package com.example.maury.saacloop;

import android.content.Intent;
import android.content.res.Configuration;
import  android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    public CrudCategoria crudCategoria = new CrudCategoria(this);
    private ListView menus_left;
    private DrawerLayout drawer;
    private String menuList[]={"Inicio","Categoria","Actividad"};
    private ActionBarDrawerToggle toggle;
    private FragmentManager fm;
    private FragmentTransaction tx;
    private Fragment fcat;
    private Fragment ftareas;
    private Fragment finicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        menus_left= findViewById(R.id.left_drawer);
        drawer = findViewById(R.id.drawer_layout);
        id = Integer.parseInt(intent.getStringExtra("ID"));
        Rut =  intent.getStringExtra("RUT");
        Log.e("Menu", id+" Rut: "+Rut);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new FragmentoInicio()).commit();
        menus_left.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menuList));
        configuracionActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        menus_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 cargaCategoria(position);
            }
        });
    }
    private void cargaCategoria(int position){
        fm = getFragmentManager();
        tx= fm.beginTransaction();
        fcat = new FragmentoCat();
        ftareas = new FragmentoTareas();
        finicio = new FragmentoInicio();
        switch (position){
            case 0:
                tx.replace(R.id.content_frame,finicio);
                break;
            case 1:
                tx.replace(R.id.content_frame, fcat);
                break;
            case 2:
                tx.replace(R.id.content_frame, ftareas);
                break;
        }
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tx.commit();
        drawer.closeDrawers();
    }
    private void configuracionActionBar(){
        toggle = new ActionBarDrawerToggle(this,drawer,R.string.drawer_open,R.string.drawer_close){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("SAAC Movil");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Seleccione");
            }
        };
        drawer.setDrawerListener(toggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoria();
        pictograma();
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
