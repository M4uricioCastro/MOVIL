package com.example.maury.saacloop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maury.saacloop.saac.Alumno;
import com.example.maury.saacloop.saac.CrudAlumno;
import com.example.maury.saacloop.saac.CrudCurso;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class loginActivity extends AppCompatActivity {
    int id;
    private TextView txtusuario, txtClave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("ID"));
        txtusuario = findViewById(R.id.txtUsuario);
        txtClave = findViewById(R.id.txtClave);
    }

    @Override
    protected void onResume() {
        super.onResume();
        alumnos();
    }
    public void alumnos(){
        String url = "http://192.168.43.58/SAAC-app-web/index.php/api/cursoAlu";
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
                Log.e("info",statusCode+"<--------------------------");
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
    public void eventoLogin(View v){
        CrudCurso crudCurso = new CrudCurso(this);
        if (txtusuario.getText().toString() != "" && txtClave.getText().toString()!=""){
           boolean esta = crudCurso.loginCurso(txtusuario.getText().toString(),txtClave.getText().toString());
           if (esta){
               Intent i = new Intent(this, alumnoActivity.class);
               i.putExtra("ID",id+"");
               startActivity(i);
           }else{
               Toast.makeText(this, "No se Encuentra", Toast.LENGTH_SHORT).show();
           }

        }else{
            Toast.makeText(this, "Campos Vacios", Toast.LENGTH_SHORT).show();
        }

    }
}
