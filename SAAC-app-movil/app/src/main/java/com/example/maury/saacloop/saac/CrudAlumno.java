package com.example.maury.saacloop.saac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CrudAlumno {
    private conexionHelper helper;
    private ContentValues values;
    private SQLiteDatabase db;

    public CrudAlumno(Context context){
        helper = new conexionHelper(context);
        values =  new ContentValues();
    }
    public void insert(Alumno a){
        try{
            db = helper.getWritableDatabase();
            values.clear();
            values.put(conexionHelper.RUT_ALUMNO, a.Rut);
            values.put(conexionHelper.NOMBRE_ALUMNO, a.Nombre);
            values.put(conexionHelper.ID_CURSO_ALUMNO, a.idCurso);
            db.insert(conexionHelper.TABLE_ALUMNO,null, values);
            db.close();
        }catch (Exception e){e.printStackTrace();}
    }
    public void delete(int id){
        String pk=id+"";
        db = helper.getWritableDatabase();
        db.delete(conexionHelper.TABLE_ALUMNO,conexionHelper.RUT_ALUMNO+"=?", new String[]{pk});
        db.close();
    }
    public void update(Alumno a){
        db = helper.getWritableDatabase();
        values.clear();
        //values.put(conexionHelper.ID_CURSO, c.idCurso);
        values.put(conexionHelper.NOMBRE_ALUMNO, a.Nombre);
        String pk = a.Rut+"";//String.valueOf(c.idCurso);
        db.update(conexionHelper.TABLE_ALUMNO,values,conexionHelper.RUT_ALUMNO+"=?", new String[]{pk});
        db.close();
    }
    public Alumno find(int id){
        Alumno a = new Alumno();
        db = helper.getReadableDatabase();
        String sql = "select * from "+conexionHelper.TABLE_ALUMNO+" where "+conexionHelper.RUT_ALUMNO+" =?";
        String pk = id+"";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        if (cursor.moveToNext()){
            a.Rut = cursor.getInt(0);
            a.Nombre = cursor.getString(1);
            a.idCurso = cursor.getInt(2);
        }
        db.close();
        return a;
    }
    public List<Alumno> AlumnoList(){
        List<Alumno> list = new ArrayList<>();
        db= helper.getReadableDatabase();
        String sql= "select * from "+conexionHelper.TABLE_ALUMNO;
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            Alumno a = new Alumno();
            a.Rut = cursor.getInt(0);
            a.Nombre = cursor.getString(1);
            a.idCurso = cursor.getInt(2);
            list.add(a);
        }
        db.close();
        return list;
    }
}
