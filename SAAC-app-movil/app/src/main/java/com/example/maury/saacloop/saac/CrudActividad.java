package com.example.maury.saacloop.saac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CrudActividad {

    private conexionHelper helper;
    private ContentValues values;
    private SQLiteDatabase db;
    public CrudActividad(Context context){
        helper = new conexionHelper(context);
        values =  new ContentValues();
    }
    public void insert(Actividad a){
        try{
            db = helper.getWritableDatabase();
            values.clear();
            values.put(conexionHelper.ID_ACTIVIDAD, a.idActividad);
            values.put(conexionHelper.ORACION, a.Oracion);
            values.put(conexionHelper.PICS_VISTA, a.PicsVista);
            values.put(conexionHelper.ID_PIC_1, a.idPic1);
            values.put(conexionHelper.ID_PIC_2, a.idPic2);
            values.put(conexionHelper.ID_PIC_3, a.idPic3);
            values.put(conexionHelper.ID_PIC_4, a.idPic4);
            values.put(conexionHelper.POS_RESPUESTA, a.PosRespuesta);
            values.put(conexionHelper.ESTADO, a.Estado);
            values.put(conexionHelper.ID_CURSO_ACTIVIDAD, a.idCurso);
            db.insert(conexionHelper.TABLE_ACTIVIDAD,null, values);
            db.close();
        }catch (Exception e){e.printStackTrace();}
    }
    public void delete(int id){
        String pk=id+"";
        db = helper.getWritableDatabase();
        db.delete(conexionHelper.TABLE_ACTIVIDAD,conexionHelper.ID_ACTIVIDAD+"=?", new String[]{pk});
        db.close();
    }
    public Actividad find(int id){
        Actividad a = new Actividad();
        db = helper.getReadableDatabase();
        String sql = "select * from "+conexionHelper.TABLE_ACTIVIDAD+" where "+conexionHelper.ID_ACTIVIDAD+" =?";
        String pk = id+"";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        if (cursor.moveToNext()){
            a.idActividad = cursor.getInt(0);
            a.Oracion = cursor.getString(1);
            a.PicsVista = cursor.getString(2);
            a.idPic1 = cursor.getInt(3);
            a.idPic2 = cursor.getInt(4);
            a.idPic3 = cursor.getInt(5);
            a.idPic4 = cursor.getInt(6);
            a.PosRespuesta = cursor.getInt(7);
            a.Estado = cursor.getString(8);
            a.idCurso = cursor.getInt(9);

        }
        db.close();
        return a;
    }
    public List<Actividad> actividadList(int id){
        String pk = id+"";
        List<Actividad> list = new ArrayList<>();
        db= helper.getReadableDatabase();
        String sql= "select * from "+conexionHelper.TABLE_ACTIVIDAD+" where "+conexionHelper.ID_CURSO_ACTIVIDAD+" =?";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        while (cursor.moveToNext()){
            Actividad a = new Actividad();
            a.idActividad = cursor.getInt(0);
            a.Oracion = cursor.getString(1);
            a.PicsVista = cursor.getString(2);
            a.idPic1 = cursor.getInt(3);
            a.idPic2 = cursor.getInt(4);
            a.idPic3 = cursor.getInt(5);
            a.idPic4 = cursor.getInt(6);
            a.PosRespuesta = cursor.getInt(7);
            a.Estado = cursor.getString(8);
            a.idCurso = cursor.getInt(9);
            list.add(a);
        }
        db.close();
        return list;
    }
    public List<String> idActividad(int id){
        String pk = id+"";
        List<String> list = new ArrayList<>();
        db= helper.getReadableDatabase();
        String sql= "select "+conexionHelper.ID_ACTIVIDAD_ALUMNO+" from "+conexionHelper.TABLE_ACTIVIDAD+" where "+conexionHelper.ID_CURSO_ACTIVIDAD+" =?";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        while (cursor.moveToNext()){
            int a =cursor.getInt(0);
            list.add(a+"");
        }
        return list;
    }
    public void updateEstado(String e,int id){
        db = helper.getWritableDatabase();
        values.clear();
        //values.put(conexionHelper.ID_CURSO, c.idCurso);
        values.put(conexionHelper.ESTADO_RESPUESTA, e);
        String pk = id+"";//String.valueOf(c.idCurso);
        db.update(conexionHelper.TABLE_ACTIVIDAD,values,conexionHelper.ID_ACTIVIDAD+"=?", new String[]{pk});
        db.close();
    }

}
