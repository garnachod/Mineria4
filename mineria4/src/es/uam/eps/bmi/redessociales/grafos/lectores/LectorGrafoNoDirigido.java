
package es.uam.eps.bmi.redessociales.grafos.lectores;

import edu.uci.ics.jung.graph.UndirectedGraph;
import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class LectorGrafoNoDirigido {
    
    public static GrafoNoDirigido leerCSV (String ruta) {
        
        FileInputStream fis = null;
        GrafoNoDirigido g = new GrafoNoDirigido();
        
        try {
            fis = new FileInputStream(ruta);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String linea = br.readLine();
            while (linea != null) {
                String[] tokens = linea.split(",");
                g.addArista(tokens[0], tokens[1]);
                linea = br.readLine();
            }
            br.close();
            isr.close();
            fis.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LectorGrafoNoDirigido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LectorGrafoNoDirigido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LectorGrafoNoDirigido.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(LectorGrafoNoDirigido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return g;
    }
    
    public static void main (String args[]) {
        
        GrafoNoDirigido g;
        
        g = LectorGrafoNoDirigido.leerCSV("datos/small1.csv");
        System.out.println(g);
        
        g = LectorGrafoNoDirigido.leerCSV("datos/small2.csv");
        System.out.println(g);
        
        g = LectorGrafoNoDirigido.leerCSV("datos/fb.csv");
        System.out.println(g);
        
        g = LectorGrafoNoDirigido.leerCSV("datos/twitter.csv");
        System.out.println(g);
        
    }
}
