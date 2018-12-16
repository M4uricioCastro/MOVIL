package com.example.maury.saacloop.saac;

public class Actividad {
    public int idActividad;
    public String Oracion;
    public String PicsVista;
    public int idPic1;
    public int idPic2;
    public int idPic3;
    public int idPic4;
    public int PosRespuesta;
    public String Estado;
    public int idCurso;

    public Actividad(){

    }

    public Actividad(int idActividad, String oracion, String picsVista, int idPic1, int idPic2, int idPic3, int idPic4, int posRespuesta, String estado, int idCurso) {
        this.idActividad = idActividad;
        Oracion = oracion;
        PicsVista = picsVista;
        this.idPic1 = idPic1;
        this.idPic2 = idPic2;
        this.idPic3 = idPic3;
        this.idPic4 = idPic4;
        PosRespuesta = posRespuesta;
        Estado = estado;
        this.idCurso = idCurso;
    }
}
