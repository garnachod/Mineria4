
package es.uam.eps.bmi.recomendacion.recomendadores;

/**
 * @author Diego Castaño y Daniel Garnacho
 * 
 * Objeto auxiliar para poder usar heaps u ordenar utilizando la posición de un elemento de una instancia
 */
public class PosicionElementoRating implements Comparable {
    private double rating;
    private int pos;
    
    public PosicionElementoRating(int pos, double rating){
        this.pos = pos;
        this.rating = rating;
    }

    /**
     * @return the rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return the pos
     */
    public int getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof PosicionElementoRating)) {
            return Integer.MAX_VALUE;
        }
        
        int posA = this.pos;
        int posB = ((PosicionElementoRating)o).pos;
        
        if (posA == posB) {
            return 0;
        }
        //queremos que se ordenen de menor a mayor
        if (posA > posB) {
            return 1;
        }
        
        return -1;
    }
}
