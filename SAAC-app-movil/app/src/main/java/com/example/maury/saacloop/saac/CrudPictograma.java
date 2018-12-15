package com.example.maury.saacloop.saac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CrudPictograma {
    private conexionHelper helper;
    private ContentValues values;
    private SQLiteDatabase db;
    public CrudPictograma(Context context){
        helper = new conexionHelper(context);
        values =  new ContentValues();
    }
    public void insert(Pictograma p){
        db = helper.getWritableDatabase();
        values.clear();
        values.put(conexionHelper.ID_PICTOGRAMA, p.idPictograma);
        values.put(conexionHelper.NOMBRE_PICTOGRAMA, p.nombre);
        values.put(conexionHelper.DESCRIPCION_PICTOGRAMA, p.descripcion);
        values.put(conexionHelper.EJEMPLO_PICTOGRAMA, p.ejemplo);
        values.put(conexionHelper.TAGS_PICTOGRAMA, p.tags);
        values.put(conexionHelper.IMG_PICTOGRAMA, p.img);
        values.put(conexionHelper.ESTADO_PICTOGRAMA, p.estado);
        values.put(conexionHelper.ID_CATEGORIA_PICTOGRAMA, p.idCategoria);
        values.put(conexionHelper.RUT_DOCENTE_PICTOGRAMA, p.RutDocente);

        db.insert(conexionHelper.TABLE_PICTOGRAMA,null, values);
        db.close();
        try{}catch (Exception e){e.printStackTrace();}
    }
    public void delete(int id){
        String pk=id+"";
        db = helper.getWritableDatabase();
        db.delete(conexionHelper.TABLE_PICTOGRAMA,conexionHelper.ID_PICTOGRAMA+"=?", new String[]{pk});
        db.close();
    }
    public void update(Pictograma p){
        db = helper.getWritableDatabase();
        values.clear();
        //values.put(conexionHelper.ID_CURSO, c.idCurso);
        values.put(conexionHelper.NOMBRE_PICTOGRAMA, p.nombre);
        values.put(conexionHelper.DESCRIPCION_PICTOGRAMA, p.descripcion);
        values.put(conexionHelper.EJEMPLO_PICTOGRAMA, p.ejemplo);
        values.put(conexionHelper.TAGS_PICTOGRAMA, p.tags);
        values.put(conexionHelper.IMG_PICTOGRAMA, p.img);
        values.put(conexionHelper.ESTADO_PICTOGRAMA, p.estado);
        values.put(conexionHelper.ID_CATEGORIA_PICTOGRAMA, p.idCategoria);
        values.put(conexionHelper.RUT_DOCENTE_PICTOGRAMA, p.RutDocente);
        String pk = p.idPictograma+"";//String.valueOf(c.idCurso);
        db.update(conexionHelper.TABLE_PICTOGRAMA,values,conexionHelper.ID_PICTOGRAMA+"=?", new String[]{pk});
        db.close();
    }
    public Pictograma find(int id){
        Pictograma p = new Pictograma();
        db = helper.getReadableDatabase();
        String sql = "select * from "+conexionHelper.TABLE_PICTOGRAMA+" where "+conexionHelper.ID_PICTOGRAMA+" =?";
        String pk = id+"";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        if (cursor.moveToNext()){
            p.idPictograma = cursor.getInt(0);
            p.nombre = cursor.getString(1);
            p.descripcion = cursor.getString(2);
            p.ejemplo = cursor.getString(3);
            p.tags = cursor.getString(4);
            p.img = cursor.getString(5);
            p.estado = cursor.getString(6);
            p.idCategoria = cursor.getInt(7);
            p.RutDocente = cursor.getInt(8);
        }
        db.close();
        return p;
    }
    public List<Pictograma> pictogramaList(){
        List<Pictograma> list = new ArrayList<>();
        db= helper.getReadableDatabase();
        String sql= "select * from "+conexionHelper.TABLE_PICTOGRAMA;
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            Pictograma p = new Pictograma();
            p.idPictograma = cursor.getInt(0);
            p.nombre = cursor.getString(1);
            p.descripcion = cursor.getString(2);
            p.ejemplo = cursor.getString(3);
            p.tags = cursor.getString(4);
            p.img = cursor.getString(5);
            p.estado = cursor.getString(6);
            p.idCategoria = cursor.getInt(7);
            p.RutDocente = cursor.getInt(8);
            list.add(p);
        }
        db.close();
        return list;
    }
    public List<Pictograma> pictoCat(int id){
        List<Pictograma> list = new ArrayList<>();
        db= helper.getReadableDatabase();
        String sql= "select * from "+conexionHelper.TABLE_PICTOGRAMA+" where "+conexionHelper.ID_CATEGORIA_PICTOGRAMA+" =?";
        String pk = id+"";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        while (cursor.moveToNext()){
            Pictograma p = new Pictograma();
            p.idPictograma = cursor.getInt(0);
            p.nombre = cursor.getString(1);
            p.descripcion = cursor.getString(2);
            p.ejemplo = cursor.getString(3);
            p.tags = cursor.getString(4);
            p.img = cursor.getString(5);
            p.estado = cursor.getString(6);
            p.idCategoria = cursor.getInt(7);
            p.RutDocente = cursor.getInt(8);
            list.add(p);
        }
        db.close();
        return list;
    }
}
