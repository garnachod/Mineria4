package es.uam.eps.bmi.redessociales.metricas;

import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;

/**
 * Interfaz común a todas la métricas de nodo
 * @author Diego Castaño y Daniel Garnacho
 */
public interface MetricaGrafo {
    public double calcular (GrafoNoDirigido grafo);
}
