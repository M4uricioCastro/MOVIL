package com.example.maury.saacloop.saac;

public class Respuesta {
    public int idActividadAlumno;
    public int Tiempo;
    public String Estado;
    public int RutAlumno;
    public int idActividad;
    public  Respuesta(){

    }

    public Respuesta(int idActividadAlumno, int tiempo, String estado, int rutAlumno, int idActividad) {
        this.idActividadAlumno = idActividadAlumno;
        Tiempo = tiempo;
        Estado = estado;
        RutAlumno = rutAlumno;
        this.idActividad = idActividad;
    }
}
