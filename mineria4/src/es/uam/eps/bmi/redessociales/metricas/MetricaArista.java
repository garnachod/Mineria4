
package es.uam.eps.bmi.redessociales.metricas;

import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;

/**
 * @author Diego Castaño y Daniel Garnacho
 */
public interface MetricaArista {
    public double calcular (GrafoNoDirigido grafo, String idNodoOrigen, String idNodoDestino);
}
