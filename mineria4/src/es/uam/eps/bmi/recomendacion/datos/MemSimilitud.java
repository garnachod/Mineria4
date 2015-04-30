package es.uam.eps.bmi.recomendacion.datos;

import es.uam.eps.bmi.recomendacion.recomendadores.PosicionElementoRating;

/**
 * @author Diego Castaño y Daniel Garnacho
 * 
 * Objeto auxiliar para poder usar heaps u ordenar similitudes.
 * Contiene un id y una similitud
 * se ordena por similitud
 */
public class MemSimilitud implements Comparable  {
    private int id;
    private double similitud;
    
    public MemSimilitud(int id, double similitud){
        this.similitud = similitud;
        this.id = id;
    }
   

    @Override
    public int compareTo(Object o) {
       if (!(o instanceof MemSimilitud)) {
            return Integer.MAX_VALUE;
        }
        
        double posA = this.getSimilitud();
        double posB = ((MemSimilitud)o).getSimilitud();
        
        if (posA == posB) {
            return 0;
        }
        //queremos que se ordenen de mayor a menor
        if (posA > posB) {
            return -1;
        }
        
        return 1;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the similitud
     */
    public double getSimilitud() {
        return similitud;
    }

    /**
     * @param similitud the similitud to set
     */
    public void setSimilitud(double similitud) {
        this.similitud = similitud;
    }
    
}
