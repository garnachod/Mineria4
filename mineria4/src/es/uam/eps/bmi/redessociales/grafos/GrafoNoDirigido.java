 
package es.uam.eps.bmi.redessociales.grafos;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class GrafoNoDirigido {
    
    private UndirectedGraph<String, Integer> grafo;
    
    public GrafoNoDirigido () {
        grafo = new UndirectedSparseGraph<>();
    }
    
    public GrafoNoDirigido (Graph<String, Integer> g) {
        grafo = new UndirectedSparseGraph<>();
        for (Integer arista : g.getEdges()) {
            Pair<String> endpoints = g.getEndpoints(arista);
            grafo.addEdge(grafo.getEdgeCount(), endpoints.getFirst(), endpoints.getSecond());
        }
    }
    
    public void addVertice (String id) {
        grafo.addVertex(id);
    }
    
    public void addArista (String idOrigen, String idDestino) {
        grafo.addEdge(grafo.getEdgeCount(), idOrigen, idDestino);
    }
    
    public ArrayList<Pair<String>> getAristas () {
        ArrayList<Pair<String>> aristas = new ArrayList<>();
        for (Integer arista : grafo.getEdges()) {
            aristas.add(grafo.getEndpoints(arista));
        }
        return aristas;
    }
    
    public ArrayList<String> getVecinos (String idNodo) {
        Collection<String> neighbors = grafo.getNeighbors(idNodo);
        if (neighbors != null) {
            return new ArrayList(neighbors);
        }
        return new ArrayList<>();
    }
    
    public int getGrado (String idNodo) {
        try {
            return grafo.degree(idNodo);
        } catch (Exception e) {
            return 0;
        }
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
    
    public Iterable<String> getVertices() {
        return grafo.getVertices();
    }
    
    public Graph<String, Integer> getGraph() {
        return grafo;
    }
    
    public static void main (String args[]) {
        GrafoNoDirigido g = new GrafoNoDirigido();
        g.addArista("dowrow", "garnachod");
        g.addArista("dowrow", "lawblob");
        g.addArista("lawblob", "dowrow");
        g.addArista("lawblob", "dowrow");
        System.out.println("Grafo no dirigido: " + g);
    }

}

