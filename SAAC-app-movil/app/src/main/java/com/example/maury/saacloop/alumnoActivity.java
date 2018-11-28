package com.example.maury.saacloop;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("ID"));
        Log.e("AlumnoActivity",id+"");

        CrudAlumno crudAlumno = new CrudAlumno(this);
        Alumno a = crudAlumno.find(6666666);
        Log.e("info","-------------------------");
        Log.e("info",a.Nombre);
        Log.e("info","--------------------------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        alumnos();
    }
    public void alumnos(){
        String url = "http://192.168.0.9/SAAC-app-web/index.php/api/cursoAlu";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.get(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    String respuesta = new String(responseBody);
                    Log.e("BDOnlineAlumno", respuesta);
                    cargaAlumnosBD(respuesta);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.w("info",statusCode+"<--------------------------");
            }
        });
    }
    public void cargaAlumnosBD(String respuesta){
        try{
            List<Alumno> list = new ArrayList<>();
            JSONArray json = new JSONArray(respuesta);
            CrudAlumno crudAlumno = new CrudAlumno(this);
            List<Alumno> ListaSize = crudAlumno.AlumnoList();
            if (ListaSize.size()<= json.length()){
                for (int i=0; i<json.length();i++){
                    Alumno a = new Alumno();
                    //eliminar los repetidos
                    crudAlumno.delete(a.Rut);
                    //ingresar todos
                    a.Rut = json.getJSONObject(i).getInt("Rut");
                    a.Nombre = json.getJSONObject(i).getString("Nombre");
                    a.idCurso = json.getJSONObject(i).getInt("idCurso");

                    crudAlumno.insert(a);

                    Log.e("info","-------------------------");
                    Log.e("info",a.Rut+"");
                    Log.e("info",a.Nombre);
                    Log.e("info","--------------------------");
                    list.add(a);
                }
            }

        }catch (Exception e){e.printStackTrace();}
    }
}
