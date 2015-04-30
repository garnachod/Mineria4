
package es.uam.eps.bmi.recomendacion.datos;
/**
 * @author Diego Castaño y Daniel Garnacho
 * 
 * Objeto auxiliar que guarda una recomendacion dado un id de elemento, se puede ordenar
 * Contiene un id y una puntuacion
 * se ordena por puntuacion
 */
public class Recomendacion implements Comparable {
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

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Recomendacion)) {
            return Integer.MAX_VALUE;
        }
        
        double posA = this.getPuntuacion();
        double posB = ((Recomendacion)o).getPuntuacion();
        
        if (posA == posB) {
            return 0;
        }
        //queremos que se ordenen de mayor a menor
        if (posA > posB) {
            return -1;
        }
        
        return 1;
    }
}
