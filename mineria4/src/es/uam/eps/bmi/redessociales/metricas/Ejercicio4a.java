
package es.uam.eps.bmi.redessociales.metricas;

import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Ejercicio 4a.
 * @author Diego Castaño y Daniel Garnacho
 */
public class Ejercicio4a {
    public static void main (String args[]) {
        PrintWriter writer = null;
        try {
            final String ficheroClustering = "bmi1415_p4_05_nodeClustering.txt";
            final String ficheroPagerank = "bmi1415_p4_05_nodePageRank.txt";
            
            ClusteringLocal cl = new ClusteringLocal();
            Pagerank pr = new Pagerank();
            
            PriorityQueue<ValorMetricaNodo> coeficientesClustering = new PriorityQueue<>();
            PriorityQueue<ValorMetricaNodo> scoresPagerank = new PriorityQueue<>();
            
            GrafoNoDirigido small1 = LectorGrafoNoDirigido.leerCSV("datos/small1.csv");
            GrafoNoDirigido small2 = LectorGrafoNoDirigido.leerCSV("datos/small2.csv");
            GrafoNoDirigido barabasi = LectorGrafoNoDirigido.leerCSV("datos/barabasi.csv");
            GrafoNoDirigido erdos = LectorGrafoNoDirigido.leerCSV("datos/erdos.csv");
            GrafoNoDirigido fb = LectorGrafoNoDirigido.leerCSV("datos/fb.csv");
            GrafoNoDirigido twitter = LectorGrafoNoDirigido.leerCSV("datos/twitter.csv");
            
            for (String nodo : small1.getVertices()) {
                coeficientesClustering.add(new ValorMetricaNodo ("small1", nodo, cl.calcular(small1, nodo)));
                scoresPagerank.add(new ValorMetricaNodo("small1", nodo, pr.calcular(small1, nodo)));
            }
            for (String nodo : small2.getVertices()) {
                coeficientesClustering.add(new ValorMetricaNodo ("small2", nodo, cl.calcular(small2, nodo)));
                scoresPagerank.add(new ValorMetricaNodo("small2", nodo, pr.calcular(small2, nodo)));
            }
            for (String nodo : barabasi.getVertices()) {
                coeficientesClustering.add(new ValorMetricaNodo ("barabasi", nodo, cl.calcular(barabasi, nodo)));
                scoresPagerank.add(new ValorMetricaNodo("barabasi", nodo, pr.calcular(barabasi, nodo)));
            }
            for (String nodo : erdos.getVertices()) {
                coeficientesClustering.add(new ValorMetricaNodo ("erdos", nodo, cl.calcular(erdos, nodo)));
                scoresPagerank.add(new ValorMetricaNodo("erdos", nodo, pr.calcular(erdos, nodo)));
            }
            for (String nodo : fb.getVertices()) {
                coeficientesClustering.add(new ValorMetricaNodo ("facebook4K", nodo, cl.calcular(fb, nodo)));
                scoresPagerank.add(new ValorMetricaNodo("facebook4K", nodo, pr.calcular(fb, nodo)));
            }
            for (String nodo : twitter.getVertices()) {
                coeficientesClustering.add(new ValorMetricaNodo ("twitter10K", nodo, cl.calcular(twitter, nodo)));
                scoresPagerank.add(new ValorMetricaNodo("twitter10K", nodo, pr.calcular(twitter, nodo)));
            }
            
            // Generar fichero clustering local
            writer = new PrintWriter(ficheroClustering, "UTF-8");
            for (int i = 0; i < 10; i++) {
                writer.println(coeficientesClustering.poll());
            }
            writer.close();
            System.out.println("Generado fichero " + ficheroClustering);
            
            // Generar fichero pagerank
            writer = new PrintWriter(ficheroPagerank, "UTF-8");
            for (int i = 0; i < 10; i++) {
                writer.println(scoresPagerank.poll());
            }
            writer.close();            
            System.out.println("Generado fichero " + ficheroPagerank);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ejercicio4a.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Ejercicio4a.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
    
}

