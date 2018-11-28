package com.example.maury.saacloop.saac;

public class Alumno {
    public int Rut;
    public String Nombre;
    public int idCurso;

    public Alumno(){

    }

    public Alumno(int rut, String nombre, int idCurso) {
        Rut = rut;
        Nombre = nombre;
        this.idCurso = idCurso;
    }
}
