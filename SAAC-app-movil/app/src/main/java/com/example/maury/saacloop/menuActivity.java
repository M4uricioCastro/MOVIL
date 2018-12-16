package com.example.maury.saacloop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.maury.saacloop.saac.Actividad;
import com.example.maury.saacloop.saac.Categoria;
import com.example.maury.saacloop.saac.CrudActividad;
import com.example.maury.saacloop.saac.CrudCategoria;
import com.example.maury.saacloop.saac.CrudPictograma;
import com.example.maury.saacloop.saac.CrudRespuesta;
import com.example.maury.saacloop.saac.Pictograma;
import com.example.maury.saacloop.saac.Respuesta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class menuActivity extends AppCompatActivity {
    int id;
    String Rut;
    private String ip="170.239.85.176";
    public static CrudCategoria crudCategoria;
    public static CrudActividad crudActividad;
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

        crudCategoria = new CrudCategoria(this);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new FragmentoInicio()).commit();
        menus_left.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menuList));
        configuracionActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        guardarPreferencia();
        menus_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 cargaCategoria(position);
            }
        });
    }
    private void guardarPreferencia(){
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("rutAlumno", Rut);
        editor.commit();
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
        tx.addToBackStack(null);
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
        actividad();
        respuesta();
       cambioEstadoAct();
    }
    public void respuesta(){
        String url = "http://"+ip+"/index.php/api/respuestas";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        int rut = Integer.parseInt(prefs.getString("rutAlumno","-1"));
        params.put("RutAlumno", rut);
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    String respuesta = new String(responseBody);
                    cargaRespuestas(respuesta);
                    Log.e("BDOrespuestas",respuesta);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("Conexion",statusCode+"");
            }
        });
    }
    public void actividad(){
        String url = "http://"+ip+"/index.php/api/actividades";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        int idShar = prefs.getInt("idCurso",-1);
        params.put("idCurso", idShar);
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode ==200){
                    String respuesta = new String(responseBody);
                    cargaActividades(respuesta);
                    Log.e("BDOactividad",respuesta);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("conexion actividad",statusCode+"");
            }
        });
    }
    public void categoria(){
        String url = "http://"+ip+"/index.php/api/categorias";
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
                Log.e("Categoria", statusCode+"<---------------------------");
            }
        });
    }
    public void cargaRespuestas(String respuesta){
        try{
            CrudRespuesta crudRespuesta = new CrudRespuesta(this);
            JSONArray json = new JSONArray(respuesta);
            SharedPreferences prefs =
                    getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
            int idShar = prefs.getInt("idCurso",-1);
            List<Respuesta> respuestaList = crudRespuesta.respuestaList(idShar);
            if (respuestaList.size()<json.length()){
                for (int i=0; i<json.length();i++){
                    Respuesta r = new Respuesta();
                    r.idActividadAlumno = json.getJSONObject(i).getInt("idActividadAlumno");
                    r.Tiempo = json.getJSONObject(i).getInt("Tiempo");
                    r.Estado = json.getJSONObject(i).getString("Estado");
                    r.RutAlumno = json.getJSONObject(i).getInt("RutAlumno");
                    r.idActividad = json.getJSONObject(i).getInt("idActividad");
                    crudRespuesta.delete(r.idActividadAlumno);
                    crudRespuesta.insert(r);
                    Log.e("info","-------------------------");
                    Log.e("info",r.idActividadAlumno+"");
                    Log.e("info","--------------------------");
                    respuestaList.add(r);
                }
            }else {Log.e("Respuesta","no hay datos nuevos");}
        }catch (Exception e){e.printStackTrace();}
    }
    public void cargaActividades(String respuesta){
        try {
            CrudActividad crudActividad = new CrudActividad(this);
            JSONArray json = new JSONArray(respuesta);
            SharedPreferences prefs =
                    getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
            int idShar = prefs.getInt("idCurso",-1);
            List<Actividad> actividadList = crudActividad.actividadList(idShar);
            if (actividadList.size()< json.length()){
                for (int i=0; i<json.length();i++){
                    Actividad a = new Actividad();
                    a.idActividad = json.getJSONObject(i).getInt("idActividad");
                    a.Oracion = json.getJSONObject(i).getString("Oracion");
                    a.PicsVista = json.getJSONObject(i).getString("PicsVista");
                    a.idPic1 = json.getJSONObject(i).getInt("idPic1");
                    a.idPic2 = json.getJSONObject(i).getInt("idPic2");
                    a.idPic3 = json.getJSONObject(i).getInt("idPic3");
                    a.idPic4 = json.getJSONObject(i).getInt("idPic4");
                    a.PosRespuesta = json.getJSONObject(i).getInt("PosRespuesta");
                    a.Estado = json.getJSONObject(i).getString("Estado");
                    a.idCurso = json.getJSONObject(i).getInt("idCurso");
                    crudActividad.delete(a.idActividad);
                    crudActividad.insert(a);
                    Log.e("info","-------------------------");
                    Log.e("info",a.idActividad+"");
                    Log.e("info","--------------------------");
                    actividadList.add(a);
                }
            }else {Log.e("Actividad","no hay datos nuevos");}
        }catch (Exception e){
            e.printStackTrace();
        }
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
                    categoriaList.add(c);
                }
            }else{Log.e("categoria","No hay Datos nuevos");}
        }catch (Exception e){e.printStackTrace();}
    }

    public void pictograma(){
        String url = "http://"+ip+"/index.php/api/pictogramas";
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
                Log.e("pictograma", statusCode+"<---------------------------");
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
   public void cambioEstadoAct(){
        CrudActividad crudActividad = new CrudActividad(this);
        CrudRespuesta crudRespuesta = new CrudRespuesta(this);
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        int idShar = prefs.getInt("idCurso",-1);
        int rut = Integer.parseInt(prefs.getString("rutAlumno","-1"));
        List<Actividad> actividadList = crudActividad.actividadList(idShar);
        List<Respuesta> respuestaList = crudRespuesta.respuestaList(rut);
        if (actividadList.isEmpty()&&respuestaList.isEmpty()){
            Log.e("Estado","Datos vacios");
        }else {
            for (int i=0;i<actividadList.size();i++){
                Actividad a = new Actividad();
                a.idActividad = actividadList.get(i).idActividad;
                for (int j=0;j<respuestaList.size();j++){
                    Respuesta r = new Respuesta();
                    r.idActividad = respuestaList.get(j).idActividad;
                    if (a.idActividad==r.idActividad){
                        crudActividad.updateEstado("Intento "+j+1,a.idActividad);
                        Log.e("Estado","intento"+0+(j+1));
                    }
                }
            }
        }



    }
}
