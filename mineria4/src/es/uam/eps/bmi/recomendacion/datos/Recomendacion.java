/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.datos;

/**
 *
 * @author dani
 */
public class Recomendacion {
    private int idElemRecom;
    private double puntuacion;
    
    public Recomendacion(int idElemRecom, double puntuacion){
        this.idElemRecom = idElemRecom;
        this.puntuacion = puntuacion;
    }

    /**
     * @return the idElemRecom
     */
    public int getIdElemRecom() {
        return idElemRecom;
    }

    /**
     * @param idElemRecom the idElemRecom to set
     */
    public void setIdElemRecom(int idElemRecom) {
        this.idElemRecom = idElemRecom;
    }

    /**
     * @return the puntuacion
     */
    public double getPuntuacion() {
        return puntuacion;
    }

    /**
     * @param puntuacion the puntuacion to set
     */
    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }
}
