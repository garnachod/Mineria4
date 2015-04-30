
package es.uam.eps.bmi.redessociales.metricas;

import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;

/**
 * Interfaz común a todas la métricas de arista
 * @author Diego Castaño y Daniel Garnacho
 */
public interface MetricaArista {
    public double calcular (GrafoNoDirigido grafo, String idNodoOrigen, String idNodoDestino);
}
