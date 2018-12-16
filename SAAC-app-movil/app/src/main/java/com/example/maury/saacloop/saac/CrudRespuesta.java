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
    public boolean insert(Respuesta r){
        try{
            db = helper.getWritableDatabase();
            values.clear();
            values.put(conexionHelper.ID_ACTIVIDAD_ALUMNO, r.idActividadAlumno);
            values.put(conexionHelper.TIEMPO, r.Tiempo);
            values.put(conexionHelper.ESTADO_RESPUESTA, r.Estado);
            values.put(conexionHelper.RUT_ALUMNO_RESPUESTA, r.RutAlumno);
            values.put(conexionHelper.ID_ACTIVIDAD_RESPUESTA, r.idActividad);

            db.insert(conexionHelper.TABLE_RESPUESTA,null, values);
            db.close();
            return true;
        }catch (Exception e){e.printStackTrace();
        return false;
        }
    }
    public void delete(int id){
        String pk=id+"";
        db = helper.getWritableDatabase();
        db.delete(conexionHelper.TABLE_RESPUESTA,conexionHelper.ID_ACTIVIDAD_ALUMNO+"=?", new String[]{pk});
        db.close();
    }
    //Se muestran todas las actividades del curso que se entro, pero el estado de estas actividades se rescata de respues
    //para saber si estan echas o no.
    //sera una consulta a todas las actividades por medio del curso(Viene del server).
    // y el estado de esta sera por medio si se ha completado o no
    public List<Respuesta> respuestaList(int id){
        String pk = id+"";
        List<Respuesta> list = new ArrayList<>();
        db= helper.getReadableDatabase();
        String sql= "select * from "+conexionHelper.TABLE_RESPUESTA+" where "+conexionHelper.RUT_ALUMNO_RESPUESTA+" =?";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        while (cursor.moveToNext()){
            Respuesta r = new Respuesta();
            r.idActividadAlumno = cursor.getInt(0);
            r.Tiempo = cursor.getInt(1);
            r.Estado = cursor.getString(2);
            r.RutAlumno = cursor.getInt(3);
            r.idActividad = cursor.getInt(4);
            list.add(r);
        }
        db.close();
        return list;
    }
    public int ultimoID(int rut){
        String pk = rut+"";
        db= helper.getReadableDatabase();
        String sql= "select "+conexionHelper.ID_ACTIVIDAD_ALUMNO+" from "+conexionHelper.TABLE_RESPUESTA+" where "+conexionHelper.RUT_ALUMNO_RESPUESTA+" =?";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        if (cursor.getCount()>0){
            return cursor.getCount();
        }else {
            return 0;
        }
    }
}
