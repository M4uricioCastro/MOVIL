package com.example.maury.saacloop;

public class Mascota {
    public int id;
    public String nombre;
    public String raza;
    public String genero;
    public double peso;

    public Mascota(){

    }

    public Mascota(int id, String nombre, String raza, String genero, double peso) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
    }

    public Mascota(String nombre, String raza, String genero, double peso) {
        this.nombre = nombre;
        this.raza = raza;
        this.genero = genero;
        this.peso = peso;
    }
}
