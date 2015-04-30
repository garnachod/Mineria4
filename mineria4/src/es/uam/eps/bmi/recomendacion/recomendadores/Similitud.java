/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.recomendadores;

import java.util.PriorityQueue;

/**
 * @author Diego Castaño y Daniel Garnacho
 * 
 * Objeto que contiene las medidas de similitud programadas
 */
public class Similitud {
    /**
     * retorna una similitud usando el metodo del coseno
     * @param heapURS
     * @param heapOtro
     * @return similitud
     */
    public static double coseno(PriorityQueue<PosicionElementoRating> heapURS, PriorityQueue<PosicionElementoRating> heapOtro){
        //sacamos de cada heap buscando coincidencias, si coinciden, se aniaden a la parte superior del coseno
        //multiplicando y siempre se anyaden al modulo
        double dividendo = 0.0;
        double moduloPrimero = 0.0;
        double moduloSegundo = 0.0;
        
        while(!heapURS.isEmpty() &&  !heapOtro.isEmpty()){
            PosicionElementoRating rat1 = heapURS.poll();
            PosicionElementoRating rat2 = heapOtro.poll();
            int compare = rat1.compareTo(rat2);
            
            if(compare == 0){
                dividendo += rat1.getRating() * rat2.getRating();
                moduloSegundo += rat2.getRating() * rat2.getRating();
                moduloPrimero += rat1.getRating() * rat1.getRating();
            }else if (compare == 1){
                moduloSegundo += rat2.getRating() * rat2.getRating();
                heapURS.add(rat1);
            }else{
                moduloPrimero += rat1.getRating() * rat1.getRating();
                heapOtro.add(rat2);
            }
             
        }
        while(!heapURS.isEmpty()){
            PosicionElementoRating rat = heapURS.poll();
            moduloPrimero += rat.getRating() * rat.getRating();
        }
        while(!heapOtro.isEmpty()){
            PosicionElementoRating rat = heapOtro.poll();
            moduloSegundo += rat.getRating() * rat.getRating();
        }
        //System.out.println(sim);
        return dividendo /(Math.sqrt(moduloPrimero)*Math.sqrt(moduloSegundo));
    }
}
