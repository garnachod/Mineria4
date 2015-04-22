
package es.uam.eps.bmi.redessociales.metricas;

import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import java.util.ArrayList;


/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class ClusteringLocal implements MetricaNodo {

    @Override
    public double calcular(GrafoNoDirigido grafo, String idNodo) {
                
        // Conexiones entre amigos del nodo O(N^2)
        int conexionesReales = 0;
        ArrayList<String> vecinos = grafo.getVecinos(idNodo);
        for (String idVecinoOrigen : vecinos) {
            for (String idVecinoDestino : vecinos) {
                if (!idVecinoOrigen.equals(idVecinoDestino) && grafo.estanConectados(idVecinoOrigen, idVecinoDestino)) {
                    conexionesReales++;                    
                }
            }
        }
        conexionesReales /= 2;
        
        // Conexiones posibles entre todos los pares de amigos
        int grado = grafo.getGrado(idNodo);
        int conexionesPosibles = ((grado * (grado - 1)) / 2);
        
        if (conexionesPosibles > 0) {
            return (conexionesReales / (double)conexionesPosibles);
        }
        return 0;        
    }
    
    public static void main (String args[]) {
        GrafoNoDirigido g = new GrafoNoDirigido();
        ClusteringLocal cl = new ClusteringLocal();
        g.addArista("a", "b");
        g.addArista("b", "c");
        g.addArista("a", "c");
        System.out.println("Clustering de b: " + cl.calcular(g, "b"));
    }
}
