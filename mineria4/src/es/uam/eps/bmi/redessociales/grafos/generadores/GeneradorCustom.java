
package es.uam.eps.bmi.redessociales.grafos.generadores;

import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.escritores.EscritorGrafoNoDirigido;
import java.util.Random;


/**
 * Genera un grafo "loner"
 * @author Diego Castaño y Daniel Garnacho
 */
public class GeneradorCustom {
    
    double probLoner = 0.0;
    final Random rn = new Random();
    
    public GeneradorCustom(double probLoner) {
        this.probLoner = probLoner;
    }
    
    public GrafoNoDirigido getGrafo(int numNodos) {
        GrafoNoDirigido g = new GrafoNoDirigido();
        
        for (int i = 0; g.getGraph().getVertexCount() < numNodos; i++) {
            if (rn.nextDouble() <= probLoner) {
                // Añadir pocas conexiones
                int ini = (rn.nextInt(g.getGraph().getVertexCount() + 1));
                int max = (rn.nextInt(3));
                for (int j = ini; j < (ini + max); j++) {
                    g.addArista(i + "", j + "");
                }
            } else {
                // Anadir muchas conexiones
                int ini = (rn.nextInt(g.getGraph().getVertexCount() + 1));
                int max = (rn.nextInt(150));
                for (int j = ini; j < (ini + max); j++) {
                    g.addArista(i + "", j + "");
                }
            }
        }
        
        return g;
    }
    
    public static void main (String args[]) {
        GeneradorCustom gc = new GeneradorCustom(0.25);
        GrafoNoDirigido g = gc.getGrafo(1000);
        EscritorGrafoNoDirigido.escribirGrafo(g, "datos/custom.csv");
    }
}
