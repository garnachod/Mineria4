
package es.uam.eps.bmi.redessociales.metricas;

import edu.uci.ics.jung.graph.util.Pair;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;

/**
 * Calcula arraigo de los arcos de un grafo no dirigido
 * @author Diego Castaño y Daniel Garnacho
 */
public class Embeddedness implements MetricaArista {

    @Override
    public double calcular(GrafoNoDirigido grafo, String idNodoOrigen, String idNodoDestino) {
        int vecinosComunes = 0;
        int gradoOrigen = grafo.getGrado(idNodoOrigen);
        int gradoDestino = grafo.getGrado(idNodoDestino);
        for (String vecino : grafo.getVecinos(idNodoOrigen)) {
            if (grafo.getVecinos(idNodoDestino).contains(vecino) && !vecino.equals(idNodoOrigen) && !vecino.equals(idNodoDestino)) {
                vecinosComunes++;
            }
        }
        int denominador = (gradoOrigen - 1) + (gradoDestino - 1) - vecinosComunes;
        if (denominador == 0) {
            return 0;
        } else {
            return (vecinosComunes / (double)denominador);
        }
    }
    
    public static void main (String args[]) {
        Embeddedness e = new Embeddedness();
        GrafoNoDirigido g = LectorGrafoNoDirigido.leerCSV("datos/small1.csv");
        System.out.println("Small1");
        for (Pair<String> arista : g.getAristas()) {
            System.out.println(arista.getFirst() + "-" + arista.getSecond() + ": " + e.calcular(g, arista.getFirst(), arista.getSecond()));
        }
        g = LectorGrafoNoDirigido.leerCSV("datos/small2.csv");
        System.out.println("Small2");
        for (Pair<String> arista : g.getAristas()) {
            System.out.println(arista.getFirst() + "-" + arista.getSecond() + ": " + e.calcular(g, arista.getFirst(), arista.getSecond()));
        }
    }

}
