package com.example.maury.saacloop.saac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maury.saacloop.Mascota;

import java.util.ArrayList;
import java.util.List;

public class CrudCurso {
    private conexionHelper helper;
    private ContentValues values;
    private SQLiteDatabase db;
    public CrudCurso(Context context){
        helper = new conexionHelper(context);
        values =  new ContentValues();
    }
    public void insert(Curso c){
        db = helper.getWritableDatabase();
        values.clear();
        values.put(conexionHelper.ID_CURSO, c.idCurso);
        values.put(conexionHelper.NOMBRE, c.Nombre);

        db.insert(conexionHelper.TABLE,null, values);
        db.close();
        try{}catch (Exception e){e.printStackTrace();}
    }
    public void delete(int id){
        String pk=id+"";
        db = helper.getWritableDatabase();
        db.delete(conexionHelper.TABLE,conexionHelper.ID_CURSO+"=?", new String[]{pk});
        db.close();
    }
    public void update(Curso c){
        db = helper.getWritableDatabase();
        values.clear();
        //values.put(conexionHelper.ID_CURSO, c.idCurso);
        values.put(conexionHelper.NOMBRE, c.Nombre);
        String pk = c.idCurso+"";//String.valueOf(c.idCurso);
        db.update(conexionHelper.TABLE,values,conexionHelper.ID_CURSO+"=?", new String[]{pk});
        db.close();
    }
    public Curso find(int id){
        Curso c = new Curso();
        db = helper.getReadableDatabase();
        String sql = "select * from "+conexionHelper.TABLE+" where "+conexionHelper.ID_CURSO+" =?";
        String pk = id+"";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        if (cursor.moveToNext()){
            c.idCurso = cursor.getInt(0);
            c.Nombre = cursor.getString(1);
        }
        db.close();
        return c;
    }
    public List<Curso> cursoList(){
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
    }
}
