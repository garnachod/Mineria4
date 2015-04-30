
package es.uam.eps.bmi.redessociales.grafos.generadores;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.escritores.EscritorGrafoNoDirigido;
import java.util.HashSet;
import org.apache.commons.collections15.Factory;


/**
 * Genera un grafo Barabasi
 * @author Diego Castaño y Daniel Garnacho
 */
public class GeneradorBarabasiAlbert {
    BarabasiAlbertGenerator bag;
    
    public GeneradorBarabasiAlbert(int numNodosIniciales) {
        Factory<Graph<String, Integer>> graphFactory = SparseGraph.getFactory();

        Factory<String> vertexFactory =
                new Factory<String>() {
            Integer count = 1;

            @Override
            public String create() {
                count++;
                return count.toString();
            }
        };
        Factory<Integer> edgeFactory =
                new Factory<Integer>() {
            Integer count = 1;

            @Override
            public Integer create() {
                return count++;
            }
        };

        HashSet<String> seedVertices = new HashSet();
        for (int i = 0; i < numNodosIniciales; i++) {
            seedVertices.add(i + "");
        }
        bag = new BarabasiAlbertGenerator(graphFactory, vertexFactory, edgeFactory, seedVertices.size(), 1, seedVertices);
    }
    
    public Graph<String, Integer> getGrafo (int numEpocas) {
        bag.evolveGraph(numEpocas);
        Graph<String, Integer> grafo = bag.create();
        return grafo;
    }
    
    public static void main (String args[]) {
        
        GeneradorBarabasiAlbert gba = new GeneradorBarabasiAlbert(1);        
        // Sacar grafo no dirigido
        GrafoNoDirigido g = new GrafoNoDirigido((Graph<String, Integer>) gba.getGrafo(1000));
        EscritorGrafoNoDirigido.escribirGrafo(g, "datos/barabasi.csv");
        
    }
}
