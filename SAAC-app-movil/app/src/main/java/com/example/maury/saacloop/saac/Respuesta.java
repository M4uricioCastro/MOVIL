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

    public int getIdActividadAlumno() {
        return idActividadAlumno;
    }

    public void setIdActividadAlumno(int idActividadAlumno) {
        this.idActividadAlumno = idActividadAlumno;
    }

    public int getTiempo() {
        return Tiempo;
    }

    public void setTiempo(int tiempo) {
        Tiempo = tiempo;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public int getRutAlumno() {
        return RutAlumno;
    }

    public void setRutAlumno(int rutAlumno) {
        RutAlumno = rutAlumno;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }
}
