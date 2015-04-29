
package es.uam.eps.bmi.redessociales.metricas;


/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class ValorMetricaGrafo implements Comparable {
    
    private String redSocial;
    private double valor;

    public ValorMetricaGrafo(String redSocial, double valor) {
        this.redSocial = redSocial;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return redSocial + " " + valor;
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass() != this.getClass()) {
            return Integer.MAX_VALUE;
        }
        if (this.valor == ((ValorMetricaGrafo) o).valor) {
            return 0;
        } else if (this.valor < ((ValorMetricaGrafo) o).valor) {
            return 1;
        }
        return -1;
    }
}
