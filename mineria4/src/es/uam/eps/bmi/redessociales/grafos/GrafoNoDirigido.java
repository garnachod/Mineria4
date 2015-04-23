 
package es.uam.eps.bmi.redessociales.grafos;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.ArrayList;

/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class GrafoNoDirigido {
    
    private UndirectedGraph<String, Integer> grafo;
    
    public GrafoNoDirigido () {
        grafo = new UndirectedSparseGraph<>();
    }
    
    public void addVertice (String id) {
        grafo.addVertex(id);
    }
    
    public void addArista (String idOrigen, String idDestino) {
        grafo.addEdge(grafo.getEdgeCount(), idOrigen, idDestino);
    }
    
    public ArrayList<String> getVecinos (String idNodo) {
        return new ArrayList(grafo.getNeighbors(idNodo));
    }
    
    public int getGrado (String idNodo) {
        return grafo.degree(idNodo);
    }
    
    public boolean estanConectados (String idNodoOrigen, String idNodoDestino) {
        Integer arista = grafo.findEdge(idNodoOrigen, idNodoDestino);
        if (arista == null) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public String toString () {
        return grafo.toString();
    }
    
    public static void main (String args[]) {
        GrafoNoDirigido g = new GrafoNoDirigido();
        g.addArista("dowrow", "garnachod");
        g.addArista("dowrow", "lawblob");
        System.out.println("Grafo no dirigido: " + g);
    }
}

