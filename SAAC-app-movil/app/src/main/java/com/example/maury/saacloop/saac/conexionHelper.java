package com.example.maury.saacloop.saac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class conexionHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="data";
    private static final int VERSION=1;

    // tabla curso
    public static final String TABLE="curso";
    public static final String ID_CURSO="idCurso";
    public static final String NOMBRE="Nombre";


    public conexionHelper (Context context){
        super(context,DATABASE_NAME, null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String script="";
        script+="create table "+TABLE+"("+ID_CURSO+" integer primary key,"+NOMBRE+" text);";
        db.execSQL(script);
        db.execSQL("insert into "+TABLE+" values(666, 'SaacPrueba');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
