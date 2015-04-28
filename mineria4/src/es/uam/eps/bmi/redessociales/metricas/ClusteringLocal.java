
package es.uam.eps.bmi.redessociales.metricas;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;
import java.util.Map;

/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class ClusteringLocal implements MetricaNodo {
    
    GrafoNoDirigido grafoActual = null;
    Map<String, Double> factores = null;
    
    @Override
    public double calcular(GrafoNoDirigido grafo, String idNodo) {
        if (grafoActual == null || !grafoActual.equals(grafo)) {
            grafoActual = grafo;
            factores = Metrics.clusteringCoefficients(grafo.getGraph());
        }
        return factores.get(idNodo);
    }
    
    public static void main (String args[]) {
        ClusteringLocal cl = new ClusteringLocal();
        GrafoNoDirigido g = LectorGrafoNoDirigido.leerCSV("datos/small2.csv");
        for (String nodo : g.getVertices()) {
            System.out.println(nodo + ": " + cl.calcular(g, nodo));
        }
    }
}
