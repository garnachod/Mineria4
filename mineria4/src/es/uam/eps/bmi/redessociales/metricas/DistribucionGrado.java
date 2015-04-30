package es.uam.eps.bmi.redessociales.metricas;

import es.uam.eps.bmi.redessociales.grafos.GrafoNoDirigido;
import es.uam.eps.bmi.redessociales.grafos.lectores.LectorGrafoNoDirigido;
import java.util.HashMap;

/**
 * Calcula la frecuencia de aparicion de cada grado en un grafo no dirigido
 * @author Diego Castaño y Daniel Garnacho
 */
public class DistribucionGrado {

    public static HashMap<Integer, Integer> getFrecuenciasGrado (GrafoNoDirigido g) {
        HashMap<Integer, Integer> frecuenciasGrado = new HashMap<>();
        
        for (String nodo : g.getVertices()) {
            int grado = g.getGrado(nodo);
            
            if (frecuenciasGrado.containsKey(grado)) {
                frecuenciasGrado.put(grado, frecuenciasGrado.get(grado) + 1);
            } else {
                frecuenciasGrado.put(grado, 1);
            }
        }
        
        return frecuenciasGrado;
    }
    
    
    public static void main(String args[]) {
        GrafoNoDirigido barabasi = LectorGrafoNoDirigido.leerCSV("datos/barabasi.csv");
        GrafoNoDirigido erdos = LectorGrafoNoDirigido.leerCSV("datos/erdos.csv");
        GrafoNoDirigido fb = LectorGrafoNoDirigido.leerCSV("datos/fb.csv");
        GrafoNoDirigido twitter = LectorGrafoNoDirigido.leerCSV("datos/twitter.csv");
        GrafoNoDirigido custom = LectorGrafoNoDirigido.leerCSV("datos/custom.csv");
        
        HashMap<Integer, Integer> frecuenciasGradoBarabasi = getFrecuenciasGrado(barabasi);
        HashMap<Integer, Integer> frecuenciasGradoErdos = getFrecuenciasGrado(erdos);
        HashMap<Integer, Integer> frecuenciasGradoFb = getFrecuenciasGrado(fb);
        HashMap<Integer, Integer> frecuenciasGradoTwitter = getFrecuenciasGrado(twitter);
        HashMap<Integer, Integer> frecuenciasGradoCustom = getFrecuenciasGrado(custom);
        
        System.out.println("Frecuencia por grados en grafo Barabasi");
        for (Integer grado : frecuenciasGradoBarabasi.keySet()) {
            System.out.println(grado + "\t" + frecuenciasGradoBarabasi.get(grado));
        }
        
        System.out.println("Frecuencia por grados en grafo Erdos");
        for (Integer grado : frecuenciasGradoErdos.keySet()) {
            System.out.println(grado + "\t" + frecuenciasGradoErdos.get(grado));
        }
        
        System.out.println("Frecuencia por grados en grafo Facebook4K");
        for (Integer grado : frecuenciasGradoFb.keySet()) {
            System.out.println(grado + "\t" + frecuenciasGradoFb.get(grado));
        }
        
        System.out.println("Frecuencia por grados en grafo Twitter10K");
        for (Integer grado : frecuenciasGradoTwitter.keySet()) {
            System.out.println(grado + "\t" + frecuenciasGradoTwitter.get(grado));
        }
        
        System.out.println("Frecuencia por grados en grafo Custom");
        for (Integer grado : frecuenciasGradoCustom.keySet()) {
            System.out.println(grado + "\t" + frecuenciasGradoCustom.get(grado));
        }
    }
}

