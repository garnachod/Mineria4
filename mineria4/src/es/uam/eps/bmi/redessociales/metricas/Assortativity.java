
package es.uam.eps.bmi.redessociales.metricas;

import edu.uci.ics.jung.graph.util.Pair;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;


/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class Assortativity implements MetricaGrafo {

    @Override
    public double calcular(GrafoNoDirigido grafo) {
        long m = grafo.getAristas().size();
        
        long sumatorioGradosCuadrado = 0;
        long sumatorioGradosCubo = 0;
        
        for (String nodo : grafo.getVertices()) {
            sumatorioGradosCuadrado += (grafo.getGrado(nodo) * grafo.getGrado(nodo));
            sumatorioGradosCubo += (grafo.getGrado(nodo) * grafo.getGrado(nodo) * grafo.getGrado(nodo));
        }
        
        long sumatorioProductoGrados = 0;
        for (Pair<String> arista : grafo.getAristas()) {
            sumatorioProductoGrados += (grafo.getGrado(arista.getFirst()) * grafo.getGrado(arista.getSecond()));
        }
        
        long denominador = (2 * m * sumatorioGradosCubo) - (sumatorioGradosCuadrado * sumatorioGradosCuadrado);
        
        if (denominador == 0) {
            return 0;
        }
        
        long numerador = (4 * m * sumatorioProductoGrados) - (sumatorioGradosCuadrado * sumatorioGradosCuadrado);
        
        return (numerador / (double)denominador);
    }
    
    public static void main (String args[]) {
        Assortativity a  = new Assortativity();
        
        GrafoNoDirigido g = LectorGrafoNoDirigido.leerCSV("datos/small1.csv");
        System.out.println("Assortativity: " + a.calcular(g));
        
        g = LectorGrafoNoDirigido.leerCSV("datos/small2.csv");
        System.out.println("Assortativity: " + a.calcular(g));
        
        g = LectorGrafoNoDirigido.leerCSV("datos/twitter.csv");
        System.out.println("Assortativity: " + a.calcular(g));
        
        g = LectorGrafoNoDirigido.leerCSV("datos/fb.csv");
        System.out.println("Assortativity: " + a.calcular(g));
    }  
}
