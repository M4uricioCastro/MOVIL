package com.example.maury.saacloop.saac;

public class Categoria {
    public int idCategoria;
    public String nombre;
    public Categoria(){

    }

    public Categoria(int idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
