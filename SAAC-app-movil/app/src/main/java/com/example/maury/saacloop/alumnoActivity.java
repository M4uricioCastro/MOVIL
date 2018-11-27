package com.example.maury.saacloop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.maury.saacloop.saac.CrudCurso;

public class alumnoActivity extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("idCurso"));
        Log.e("info",id+"");
    }
}
