package com.example.maury.saacloop;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private TextView txtusuario, txtClave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("ID"));
        Log.e("AlumnoActivity",id+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
