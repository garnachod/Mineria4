
package es.uam.eps.bmi.recomendacion.recomendadores;

/**
 * @author Diego Castaño y Daniel Garnacho
 * 
 * Objeto auxiliar para obtener un rating dado la suma de ratings y la similitud de esos ratings.
 */
public class AuxObtenerRating {
    private double ratingPorSim = 0;
    private double sim = 0;
    private int incidencias = 0;
    /**
     * Añande un rating dada una similitud
     * @param rating 
     * @param similitud 
     */
    public void addElement(double rating, double similitud){
        this.ratingPorSim += rating * similitud;
        this.sim += similitud;
        this.incidencias++;
    }
    /**
     * retorna el rating final calculado
     * @return rating simulado
     */
    public double getRating(){
        if(this.sim == 0.0){
            return 0.0;
        }
        return this.ratingPorSim / this.sim;
    }
    /**
     * retorna el numero de incidencias que ha tenido este rating
     * @return num de incidencias
     */
    public int getIncidencias(){
        return this.incidencias;
    }
}
