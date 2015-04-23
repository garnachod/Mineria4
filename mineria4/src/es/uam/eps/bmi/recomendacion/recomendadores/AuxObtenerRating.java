/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.recomendadores;

/**
 *
 * @author dani
 */
public class AuxObtenerRating {
    private double ratingPorSim = 0;
    private double sim = 0;
    private int incidencias = 0;
    
    public void addElement(double rating, double similitud){
        this.ratingPorSim += rating * similitud;
        this.sim += similitud;
        this.incidencias++;
    }
    
    public double getRating(){
        if(this.sim == 0.0){
            return 0.0;
        }
        return this.ratingPorSim / this.sim;
    }
    public int getIncidencias(){
        return this.incidencias;
    }
}
