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

    public int getIdActividad() {
        return idActividad;
    }

    public String getOracion() {
        return Oracion;
    }

    public String getPicsVista() {
        return PicsVista;
    }

    public int getIdPic1() {
        return idPic1;
    }

    public int getIdPic2() {
        return idPic2;
    }

    public int getIdPic3() {
        return idPic3;
    }

    public int getIdPic4() {
        return idPic4;
    }

    public int getPosRespuesta() {
        return PosRespuesta;
    }

    public String getEstado() {
        return Estado;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public void setOracion(String oracion) {
        Oracion = oracion;
    }

    public void setPicsVista(String picsVista) {
        PicsVista = picsVista;
    }

    public void setIdPic1(int idPic1) {
        this.idPic1 = idPic1;
    }

    public void setIdPic2(int idPic2) {
        this.idPic2 = idPic2;
    }

    public void setIdPic3(int idPic3) {
        this.idPic3 = idPic3;
    }

    public void setIdPic4(int idPic4) {
        this.idPic4 = idPic4;
    }

    public void setPosRespuesta(int posRespuesta) {
        PosRespuesta = posRespuesta;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
}
