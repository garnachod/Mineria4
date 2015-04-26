
package es.uam.eps.bmi.redessociales.metricas;

import edu.uci.ics.jung.graph.util.Pair;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;

/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class Embededness implements MetricaArista {

    @Override
    public double calcular(GrafoNoDirigido grafo, String idNodoOrigen, String idNodoDestino) {
        int vecinosComunes = 0;
        int gradoOrigen = grafo.getGrado(idNodoOrigen);
        int gradoDestino = grafo.getGrado(idNodoDestino);
        for (String vecino : grafo.getVecinos(idNodoOrigen)) {
            if (grafo.getVecinos(idNodoDestino).contains(vecino)) {
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
        Embededness e = new Embededness();
        GrafoNoDirigido g = LectorGrafoNoDirigido.leerCSV("datos/twitter.csv");
        for (Pair<String> arista : g.getAristas()) {
            System.out.println(arista.getFirst() + "-" + arista.getSecond() + ": " + e.calcular(g, arista.getFirst(), arista.getSecond()));
        }
    }

}
