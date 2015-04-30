
package es.uam.eps.bmi.redessociales.metricas;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;
import java.util.Map;


/**
 * Cálculo de clustering global
 * @author Diego Castaño y Daniel Garnacho
 */
public class ClusteringGlobal implements MetricaGrafo {

    /**
     * Calculado como promedio de los coef. locales
     * Sobreestima para grafos con muchos nodos de grado bajo
     * pero es menos costoso y habitual para grafos grandes
     * Ver: http://stackoverflow.com/questions/6643555/graph-theory-calculating-clustering-coefficient
     */
    @Override
    public double calcular(GrafoNoDirigido grafo) {
        double media = 0.0;
        Map<String, Double> factores = Metrics.clusteringCoefficients(grafo.getGraph());
        for (String nodo : grafo.getVertices()) {
            media += factores.get(nodo);
        }
        if (!factores.isEmpty()) {
            media /= (double)factores.size();
            return media;
        }
        return 0;
    }
    
    public static void main (String args[]) {
        ClusteringGlobal cg = new ClusteringGlobal();
        GrafoNoDirigido g = LectorGrafoNoDirigido.leerCSV("datos/small2.csv");
        System.out.println("Global: " + cg.calcular(g));
    }    
}
