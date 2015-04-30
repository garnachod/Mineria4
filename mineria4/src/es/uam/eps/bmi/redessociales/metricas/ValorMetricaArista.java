
package es.uam.eps.bmi.redessociales.metricas;


/**
 * Bean para ordenar valores de métricas de una arista
 * @author Diego Castaño y Daniel Garnacho
 */
public class ValorMetricaArista implements Comparable {

    private String redSocial;
    private String idNodoOrigen;
    private String idNodoDestino;
    private double valor;

    public ValorMetricaArista(String redSocial, String idNodoOrigen, String idNodoDestino, double valor) {
        this.redSocial = redSocial;
        this.idNodoOrigen = idNodoOrigen;
        this.idNodoDestino = idNodoDestino;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return redSocial + " " + idNodoOrigen + " " + idNodoDestino + " " + valor;
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass() != this.getClass()) {
            return Integer.MAX_VALUE;
        }
        if (this.valor == ((ValorMetricaArista) o).valor) {
            return 0;
        } else if (this.valor < ((ValorMetricaArista) o).valor) {
            return 1;
        }
        return -1;
    }
}