package com.example.maury.saacloop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class insertarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
    }
    public void insertarMascota(Mascota m){
        String url = "http://raspberry.todojava.net/index.php/welcome/insertMascota";
        RequestParams params = new RequestParams();
        params.put("nombre", m.nombre);
        params.put("raza", m.raza);
        params.put("genero", m.genero);
        params.put("peso", m.peso);
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
    public void insertar(View view) {
        insertarMascota(new Mascota("asd","pichu","rayo",1.2));
        finish();
    }
}
