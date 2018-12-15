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
    public Actividad find(int id){
        Actividad a = new Actividad();
        db = helper.getReadableDatabase();
        String sql = "select * from "+conexionHelper.TABLE_ACTIVIDAD+" where "+conexionHelper.ID_ACTIVIDAD+" =?";
        String pk = id+"";
        Cursor cursor = db.rawQuery(sql,new String[]{pk});
        if (cursor.moveToNext()){
            a.idActividad = cursor.getInt(0);
            a.Oracion = cursor.getString(1);
            a.PicsVista = cursor.getInt(2);
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
    public List<Actividad> actividadList(){
        List<Actividad> list = new ArrayList<>();
        db= helper.getReadableDatabase();
        String sql= "select * from "+conexionHelper.TABLE;
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            Actividad a = new Actividad();
            a.idActividad = cursor.getInt(0);
            a.Oracion = cursor.getString(1);
            a.PicsVista = cursor.getInt(2);
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

}
