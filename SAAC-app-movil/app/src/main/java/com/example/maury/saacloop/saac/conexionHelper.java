package com.example.maury.saacloop.saac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class conexionHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="data";
    private static final int VERSION=1;
    private Context con;

    // tabla curso
    public static final String TABLE="curso";
    public static final String ID_CURSO="idCurso";
    public static final String NOMBRE="Nombre";
    //tabla alumno
    public static final String TABLE_ALUMNO="alumno";
    public static final String RUT_ALUMNO="Rut";
    public static final String NOMBRE_ALUMNO="Nombre";
    public static final String ID_CURSO_ALUMNO="idCurso";
    //tabla categoria
    public static final String TABLE_CATEGORIA="categoria";
    public static final String ID_CATEGORIA="idCategoria";
    public static final String NOMBRE_CATEGORIA="Nombre";
    //pictogramas
    public static final String TABLE_PICTOGRAMA="pictograma";
    public static final String ID_PICTOGRAMA="idPictograma";
    public static final String NOMBRE_PICTOGRAMA="Nombre";
    public static final String DESCRIPCION_PICTOGRAMA="Descripcion";
    public static final String EJEMPLO_PICTOGRAMA="Ejemplo";
    public static final String TAGS_PICTOGRAMA="Tags";
    public static final String IMG_PICTOGRAMA="img";
    public static final String ESTADO_PICTOGRAMA="Estado";
    public static final String ID_CATEGORIA_PICTOGRAMA="idCategoria";
    public static final String RUT_DOCENTE_PICTOGRAMA="RutDocente";
    //tabla Actividad
    public static final String TABLE_ACTIVIDAD="actividad";
    public static final String ID_ACTIVIDAD="idActividad";
    public static final String ORACION="Oracion";
    public static final String PICS_VISTA="PicsVista";
    public static final String ID_PIC_1="idPic1";
    public static final String ID_PIC_2="idPic2";
    public static final String ID_PIC_3="idPic3";
    public static final String ID_PIC_4="idPic4";
    public static final String POS_RESPUESTA="PosRespuesta";
    public static final String ESTADO="Estado";
    public static final String ID_CURSO_ACTIVIDAD="idCurso";
    //TABLA RESPUESTA
    public static final String TABLE_RESPUESTA="respuesta";
    public static final String ID_ACTIVIDAD_ALUMNO="idActividadAlumno";
    public static final String TIEMPO="Tiempo";
    public static final String ESTADO_RESPUESTA="Estado";
    public static final String RUT_ALUMNO_RESPUESTA="RutAlumno";
    public static final String ID_ACTIVIDAD_RESPUESTA="idActividad";

    public conexionHelper (Context context){
        super(context,DATABASE_NAME, null,VERSION);
        this.con =context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String script="";
        script+="create table "+TABLE+"("+ID_CURSO+" integer primary key,"+NOMBRE+" text);";
        db.execSQL(script);
        //db.execSQL("insert into "+TABLE+" values(666, 'SaacPrueba');");
        //alumno
        String scriptAlumno="";
        scriptAlumno+="create table "+TABLE_ALUMNO+"("+RUT_ALUMNO+" integer primary key,"+NOMBRE_ALUMNO+" text,"+ID_CURSO_ALUMNO+" integer);";
        db.execSQL(scriptAlumno);
        //db.execSQL("insert into "+TABLE_ALUMNO+" values(6666666, 'SAACalumno',666);");
        //categoria
        String scriptCategoria="";
        scriptCategoria+="create table "+TABLE_CATEGORIA+"("+ID_CATEGORIA+" integer primary key,"+NOMBRE_CATEGORIA+" text);";
        db.execSQL(scriptCategoria);
        //pictograma
        String scriptPictograma="";
        scriptPictograma+="create table "+TABLE_PICTOGRAMA+"("+ID_PICTOGRAMA+" integer primary key,"+NOMBRE_PICTOGRAMA+" text,"+DESCRIPCION_PICTOGRAMA+" text,"+EJEMPLO_PICTOGRAMA+" text,"+TAGS_PICTOGRAMA+" text,"+IMG_PICTOGRAMA+" text,"+ESTADO_PICTOGRAMA+" text,"+ID_CATEGORIA_PICTOGRAMA+" integer,"+RUT_DOCENTE_PICTOGRAMA+" integer);";
        db.execSQL(scriptPictograma);
        //actividad
        String scriptActividad="";
        scriptActividad+="create table "+TABLE_ACTIVIDAD+"("+ID_ACTIVIDAD+" integer primary key, "+ORACION+" text, "+PICS_VISTA+" text, "
                +ID_PIC_1+" integer, "+ID_PIC_2+" integer , "+ID_PIC_3+" integer, "+ID_PIC_4+" integer, "+POS_RESPUESTA+" int, "+ESTADO
                +" text, "+ID_CURSO_ACTIVIDAD+" integer)";
        db.execSQL(scriptActividad);
        //respuesta
        String scriptRespuesta="";
        scriptRespuesta+="create table "+TABLE_RESPUESTA+"("+ID_ACTIVIDAD_ALUMNO+" integer primary key, "+TIEMPO+" integer, "+
                ESTADO_RESPUESTA+" text, "+RUT_ALUMNO_RESPUESTA+" integer, "+ID_ACTIVIDAD_RESPUESTA+" integer)";
        db.execSQL(scriptRespuesta);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        con.deleteDatabase("data");
        onCreate(db);
    }
}
