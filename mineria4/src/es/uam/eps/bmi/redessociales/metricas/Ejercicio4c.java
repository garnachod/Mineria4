
package es.uam.eps.bmi.redessociales.metricas;

import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class Ejercicio4c {
    public static void main (String args[]) {
        PrintWriter writer = null;
        try {
            final String ficheroClustering = "bmi1415_p4_05_graphClustering.txt";
            final String ficheroAssortativity = "bmi1415_p4_05_graphAssortativity.txt";

            ClusteringGlobal cg = new ClusteringGlobal();
            Assortativity a = new Assortativity();
            
            PriorityQueue<ValorMetricaGrafo> clustering = new PriorityQueue<>();
            PriorityQueue<ValorMetricaGrafo> assortativity = new PriorityQueue<>();

            GrafoNoDirigido small1 = LectorGrafoNoDirigido.leerCSV("datos/small1.csv");
            GrafoNoDirigido small2 = LectorGrafoNoDirigido.leerCSV("datos/small2.csv");
            GrafoNoDirigido barabasi = LectorGrafoNoDirigido.leerCSV("datos/barabasi.csv");
            GrafoNoDirigido erdos = LectorGrafoNoDirigido.leerCSV("datos/erdos.csv");
            GrafoNoDirigido fb = LectorGrafoNoDirigido.leerCSV("datos/fb.csv");
            GrafoNoDirigido twitter = LectorGrafoNoDirigido.leerCSV("datos/twitter.csv");
            
            clustering.add(new ValorMetricaGrafo("small1", cg.calcular(small1)));
            clustering.add(new ValorMetricaGrafo("small2", cg.calcular(small2)));
            clustering.add(new ValorMetricaGrafo("barabasi", cg.calcular(barabasi)));
            clustering.add(new ValorMetricaGrafo("erdos", cg.calcular(erdos)));
            clustering.add(new ValorMetricaGrafo("facebook4K", cg.calcular(fb)));
            clustering.add(new ValorMetricaGrafo("twitter10K", cg.calcular(twitter)));
            
            assortativity.add(new ValorMetricaGrafo("small1", a.calcular(small1)));
            assortativity.add(new ValorMetricaGrafo("small2", a.calcular(small2)));
            assortativity.add(new ValorMetricaGrafo("barabasi", a.calcular(barabasi)));
            assortativity.add(new ValorMetricaGrafo("erdos", a.calcular(erdos)));
            assortativity.add(new ValorMetricaGrafo("facebook4K", a.calcular(fb)));
            assortativity.add(new ValorMetricaGrafo("twitter10K", a.calcular(twitter)));
            
            // Genera fichero clustering
            writer = new PrintWriter(ficheroClustering);
            while (!clustering.isEmpty()) {
                writer.println(clustering.poll());
            }
            writer.close();
            System.out.println("Generado fichero " + ficheroClustering);
            
            // Genera fichero 
            writer = new PrintWriter(ficheroAssortativity);
            while (!assortativity.isEmpty()) {
                writer.println(assortativity.poll());
            }
            writer.close();
            System.out.println("Generado fichero " + ficheroAssortativity);
            
            
        } catch (IOException ex) {
            Logger.getLogger(Ejercicio4b.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
}


