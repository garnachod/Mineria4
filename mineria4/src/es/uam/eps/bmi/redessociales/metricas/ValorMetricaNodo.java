package es.uam.eps.bmi.redessociales.metricas;

/**
 * Bean para ordenar valores de métricas de un nodo
 * @author Diego Castaño y Daniel Garnacho
 */
class ValorMetricaNodo implements Comparable {

    private String redSocial;
    private String idNodo;
    private double valor;

    public ValorMetricaNodo(String redSocial, String idNodo, double valor) {
        this.redSocial = redSocial;
        this.idNodo = idNodo;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return redSocial + " " + idNodo + " " + valor;
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass() != this.getClass()) {
            return Integer.MAX_VALUE;
        }
        if (this.valor == ((ValorMetricaNodo) o).valor) {
            return 0;
        } else if (this.valor < ((ValorMetricaNodo) o).valor) {
            return 1;
        }
        return -1;
    }
}