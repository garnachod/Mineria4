
package es.uam.eps.bmi.redessociales.grafos.generadores;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import java.util.HashSet;
import org.apache.commons.collections15.Factory;


/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class GeneradorBarabasiAlbert {
    BarabasiAlbertGenerator bag;
    
    public GeneradorBarabasiAlbert(int numNodosIniciales) {
        Factory<Graph<String, Integer>> graphFactory = SparseGraph.getFactory();

        Factory<Integer> vertexFactory =
                new Factory<Integer>() {
            int count = 1;

            @Override
            public Integer create() {
                return count++;
            }
        };
        Factory<String> edgeFactory =
                new Factory<String>() {
            Integer count = 1;

            @Override
            public String create() {
                count++;
                return count.toString();
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
        System.out.println(gba.getGrafo(1000));
    }
}
