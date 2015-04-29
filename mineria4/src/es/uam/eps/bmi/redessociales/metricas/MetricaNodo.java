package es.uam.eps.bmi.redessociales.metricas;

import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;

/**
 * @author Diego Castaño y Daniel Garnacho
 */
public interface MetricaNodo {
    /**
     *
     * @param grafo
     * @param idNodo
     * @return
     */
    public double calcular (GrafoNoDirigido grafo, String idNodo);
    
}
