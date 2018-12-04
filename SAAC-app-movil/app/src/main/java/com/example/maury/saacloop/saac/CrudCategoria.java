package com.example.maury.saacloop.saac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CrudCategoria {
    private conexionHelper helper;
    private ContentValues values;
    private SQLiteDatabase db;
    public CrudCategoria(Context context){
        helper = new conexionHelper(context);
        values =  new ContentValues();
    }
    public void insert(Categoria c){
        db = helper.getWritableDatabase();
        values.clear();
        values.put(conexionHelper.ID_CATEGORIA, c.idCategoria);
        values.put(conexionHelper.NOMBRE_CATEGORIA, c.nombre);

        db.insert(conexionHelper.TABLE_CATEGORIA,null, values);
        db.close();
        try{}catch (Exception e){e.printStackTrace();}
    }
    public void delete(int id){
        String pk=id+"";
        db = helper.getWritableDatabase();
        db.delete(conexionHelper.TABLE_CATEGORIA,conexionHelper.ID_CATEGORIA+"=?", new String[]{pk});
        db.close();
    }
    public void update(Categoria c){
        db = helper.getWritableDatabase();
        values.clear();
        //values.put(conexionHelper.ID_CURSO, c.idCurso);
        values.put(conexionHelper.NOMBRE_CATEGORIA, c.nombre);
        String pk = c.idCategoria+"";//String.valueOf(c.idCurso);
        db.update(conexionHelper.TABLE_CATEGORIA,values,conexionHelper.ID_CATEGORIA+"=?", new String[]{pk});
        db.close();
    }
    public Categoria find(int id){
        Categoria c = new Categoria();
        db = helper.getReadableDatabase();
        String sql = "select * from "+conexionHelper.TABLE_CATEGORIA+" where "+conexionHelper.ID_CATEGORIA+" =?";
        String pk = id+"";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        if (cursor.moveToNext()){
            c.idCategoria = cursor.getInt(0);
            c.nombre = cursor.getString(1);
        }
        db.close();
        return c;
    }
    public List<Categoria> categoriaList(){
        List<Categoria> list = new ArrayList<>();
        db= helper.getReadableDatabase();
        String sql= "select * from "+conexionHelper.TABLE_CATEGORIA;
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            Categoria c = new Categoria();
            c.idCategoria = cursor.getInt(0);
            c.nombre = cursor.getString(1);
            list.add(c);
        }
        db.close();
        return list;
    }

}
