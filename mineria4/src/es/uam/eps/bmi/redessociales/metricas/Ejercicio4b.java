
package es.uam.eps.bmi.redessociales.metricas;

import edu.uci.ics.jung.graph.util.Pair;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Ejercicio 4b
 * @author Diego Castaño y Daniel Garnacho
 */
public class Ejercicio4b {
    public static void main (String args[]) {
        PrintWriter writer = null;
        try {
            final String ficheroEmbeddedness = "bmi1415_p4_05_edgeEmbededness.txt";
            Embeddedness e = new Embeddedness();
            
            PriorityQueue<ValorMetricaArista> embeddednes = new PriorityQueue<>();
            
            GrafoNoDirigido small1 = LectorGrafoNoDirigido.leerCSV("datos/small1.csv");
            GrafoNoDirigido small2 = LectorGrafoNoDirigido.leerCSV("datos/small2.csv");
            GrafoNoDirigido barabasi = LectorGrafoNoDirigido.leerCSV("datos/barabasi.csv");
            GrafoNoDirigido erdos = LectorGrafoNoDirigido.leerCSV("datos/erdos.csv");
            GrafoNoDirigido fb = LectorGrafoNoDirigido.leerCSV("datos/fb.csv");
            GrafoNoDirigido twitter = LectorGrafoNoDirigido.leerCSV("datos/twitter.csv");
            
            for (Pair<String> arista : small1.getAristas()) {
                embeddednes.add(new ValorMetricaArista("small1", arista.getFirst(), arista.getSecond(), e.calcular(small1, arista.getFirst(), arista.getSecond())));
            }
            for (Pair<String> arista : small2.getAristas()) {
                embeddednes.add(new ValorMetricaArista("small2", arista.getFirst(), arista.getSecond(), e.calcular(small2, arista.getFirst(), arista.getSecond())));
            }
            for (Pair<String> arista : barabasi.getAristas()) {
                embeddednes.add(new ValorMetricaArista("barabasi", arista.getFirst(), arista.getSecond(), e.calcular(barabasi, arista.getFirst(), arista.getSecond())));
            }
            for (Pair<String> arista : erdos.getAristas()) {
                embeddednes.add(new ValorMetricaArista("erdos", arista.getFirst(), arista.getSecond(), e.calcular(erdos, arista.getFirst(), arista.getSecond())));
            }
            for (Pair<String> arista : fb.getAristas()) {
                embeddednes.add(new ValorMetricaArista("facebook4K", arista.getFirst(), arista.getSecond(), e.calcular(fb, arista.getFirst(), arista.getSecond())));
            }
            for (Pair<String> arista : twitter.getAristas()) {
                embeddednes.add(new ValorMetricaArista("twitter10K", arista.getFirst(), arista.getSecond(), e.calcular(twitter, arista.getFirst(), arista.getSecond())));
            }
            writer = new PrintWriter(ficheroEmbeddedness);
            for (int i = 0; i < 10; i++) {
                writer.println(embeddednes.poll());
            }
            writer.close();
            System.out.println("Generado fichero " + ficheroEmbeddedness);
        } catch (IOException ex) {
            Logger.getLogger(Ejercicio4b.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
}


