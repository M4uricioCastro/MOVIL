package com.example.maury.saacloop.saac;

public class Pictograma {
    public int idPictograma;
    public String nombre;
    public String descripcion;
    public String ejemplo;
    public String tags;
    public String img;
    public String estado;
    public int idCategoria;
    public int RutDocente;
public Pictograma (){

}

    public Pictograma(int idPictograma, String nombre, String descripcion, String ejemplo, String tags, String img, String estado, int idCategoria, int rutDocente) {
        this.idPictograma = idPictograma;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ejemplo = ejemplo;
        this.tags = tags;
        this.img = img;
        this.estado = estado;
        this.idCategoria = idCategoria;
        RutDocente = rutDocente;
    }
}
