
package es.uam.eps.bmi.redessociales.grafos.generadores;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.escritores.EscritorGrafoNoDirigido;
import java.util.HashSet;
import org.apache.commons.collections15.Factory;


/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class GeneradorErdosRenyi {
    ErdosRenyiGenerator erg;
    
    public GeneradorErdosRenyi(int numNodosIniciales) {
        Factory<UndirectedGraph<String, Integer>> graphFactory = UndirectedSparseGraph.getFactory();

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

        HashSet<String> seedVertices = new HashSet<>();
        for (int i = 0; i < numNodosIniciales; i++) {
            seedVertices.add(i + "");
        }
        erg = new ErdosRenyiGenerator(graphFactory, vertexFactory, edgeFactory, seedVertices.size(), 0.001);
    }
    
    public Graph<String, Integer> getGrafo () {
        Graph<String, Integer> grafo = (Graph<String, Integer>) erg.create();
        return grafo;
    }
    
    public static void main (String args[]) {
        
        GeneradorErdosRenyi ger = new GeneradorErdosRenyi(1000);
        // Sacar grafo no dirigido
        GrafoNoDirigido g = new GrafoNoDirigido(ger.getGrafo());
        EscritorGrafoNoDirigido.escribirGrafo(g, "datos/erdos.csv");
        
    }
}
