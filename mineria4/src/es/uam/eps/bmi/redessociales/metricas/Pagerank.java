
package es.uam.eps.bmi.redessociales.metricas;

import edu.uci.ics.jung.algorithms.scoring.PageRank;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;


/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class Pagerank implements MetricaNodo {

    GrafoNoDirigido grafoActual = null;
    PageRank<String, Integer> ranker = null;
    
    @Override
    public double calcular(GrafoNoDirigido grafo, String idNodo) {
        if (grafoActual == null || !grafoActual.equals(grafo)){
            grafoActual = grafo;
            ranker = new PageRank<>(grafo.getGraph(), 0.15);
            ranker.setTolerance(0.001);
            ranker.setMaxIterations(40);
            ranker.evaluate();
        }
        
        return (Double) ranker.getVertexScore(idNodo);
    }
    public static void main (String args[]) {
        Pagerank pr = new Pagerank();
        GrafoNoDirigido g = LectorGrafoNoDirigido.leerCSV("datos/twitter.csv");
        for (String nodo : g.getVertices()) {
            System.out.println(nodo + ": " + pr.calcular(g, nodo));
        }
    }
    
}
