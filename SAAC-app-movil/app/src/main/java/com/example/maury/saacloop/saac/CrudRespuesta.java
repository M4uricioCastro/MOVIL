package com.example.maury.saacloop.saac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CrudRespuesta {

    private conexionHelper helper;
    private ContentValues values;
    private SQLiteDatabase db;
    public CrudRespuesta(Context context){
        helper = new conexionHelper(context);
        values =  new ContentValues();
    }
    public void insert(Respuesta r){
        db = helper.getWritableDatabase();
        values.clear();
        values.put(conexionHelper.ID_ACTIVIDAD_ALUMNO, r.idActividadAlumno);
        values.put(conexionHelper.TIEMPO, r.Tiempo);
        values.put(conexionHelper.ESTADO_RESPUESTA, r.Estado);
        values.put(conexionHelper.RUT_ALUMNO_RESPUESTA, r.RutAlumno);
        values.put(conexionHelper.ID_ACTIVIDAD_RESPUESTA, r.idActividad);

        db.insert(conexionHelper.TABLE_RESPUESTA,null, values);
        db.close();
        try{}catch (Exception e){e.printStackTrace();}
    }
    //Se muestran todas las actividades del curso que se entro, pero el estado de estas actividades se rescata de respues
    //para saber si estan echas o no.
    //sera una consulta a todas las actividades por medio del curso(Viene del server).
    // y el estado de esta sera por medio si se ha completado o no
    /*public List<Curso> cursoList(){
        List<Curso> list = new ArrayList<>();
        db= helper.getReadableDatabase();
        String sql= "select * from "+conexionHelper.TABLE;
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            Curso c = new Curso();
            c.idCurso = cursor.getInt(0);
            c.Nombre = cursor.getString(1);
            list.add(c);
        }
        db.close();
        return list;
    }*/
}
