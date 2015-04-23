package es.uam.eps.bmi.redessociales.grafos.escritores;

import edu.uci.ics.jung.graph.util.Pair;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class EscritorGrafoNoDirigido {

    public static void escribirGrafo(GrafoNoDirigido grafo, String rutaFichero) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(rutaFichero);
            for (Pair<String> parNodos : grafo.getAristas()) {
                writer.append(parNodos.getFirst() + "," + parNodos.getSecond() + "\n");
            }
            writer.flush();
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(EscritorGrafoNoDirigido.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(EscritorGrafoNoDirigido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
