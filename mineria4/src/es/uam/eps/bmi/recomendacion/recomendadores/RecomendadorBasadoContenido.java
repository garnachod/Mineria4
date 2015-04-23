/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.recomendadores;

import es.uam.eps.bmi.recomendacion.datos.Instance;
import es.uam.eps.bmi.recomendacion.datos.Instances;
import es.uam.eps.bmi.recomendacion.datos.MemSimilitud;
import es.uam.eps.bmi.recomendacion.datos.Recomendacion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author dani
 */
public class RecomendadorBasadoContenido {
    private PriorityQueue<MemSimilitud> similitudes;
    private HashMap<Integer, Instances> InstancesDeIDElem;
    private int K = 20;
    
    public List<Recomendacion> recomiendaUsuarioElem(String TagUsuario,String TagRating,String TagIDElem, String tagIDStr,String tagRate, int idUsuario, int idElem, Instances instanciasRated, Instances instanciasDataInfo){
        ArrayList<Recomendacion> recomendacion = new ArrayList<>();
        this.similitudes = new PriorityQueue();
        //posicion para las busquedas
        int posInformacionUsarioRated = instanciasRated.getPosFromColumn(TagUsuario);
        int posRatingRated = instanciasRated.getPosFromColumn(TagRating);
        int posIDElemRated = instanciasRated.getPosFromColumn(TagIDElem);
        int posIDElemTag = instanciasDataInfo.getPosFromColumn(TagIDElem);
        int posTagIDTag = instanciasDataInfo.getPosFromColumn(tagIDStr);
        int posTagRate = instanciasDataInfo.getPosFromColumn(tagRate);
        //cogemos las instancias votadas solo de un usuario dado
        Instances informacionUsuario = instanciasRated.getListInstancesWhereColumnEquals(TagUsuario, idUsuario);
        if(informacionUsuario.nInstances() <= 0){
            return recomendacion;
        }
        System.out.println("usuario encontrado");
        //buscamos las Instancias de una pelicula
        //para eso primero buscamos los id de pelicula
        ArrayList<Integer> idsElemNoRep = instanciasDataInfo.getListaIDNoRepetidosColumna(TagIDElem);
        if(idsElemNoRep.size() <= 0){
            return recomendacion;
        }
        //
        this.InstancesDeIDElem = new HashMap<>();
        for(int identificador:idsElemNoRep){
            Instances inst = instanciasDataInfo.getListInstancesWhereColumnEquals(TagIDElem, identificador);
            instanciasDataInfo = instanciasDataInfo.getListInstancesWhereColumnDistinct(TagIDElem, identificador);
            this.InstancesDeIDElem.put(identificador, inst);
        }
        System.out.println("Instances ordenadas");
        //Instances elemBase = instanciasDataInfo.getListInstancesWhereColumnEquals(TagIDElem, idElem);
        Instances elemBase = this.InstancesDeIDElem.get(idElem);
        for(int identificadorElem : idsElemNoRep){
            if(identificadorElem == idElem){
                continue;
            }
            //System.out.println(identificadorElem);
            PriorityQueue<PosicionElementoRating> heapElemBase = new PriorityQueue();
            for(Instance dataElemBase : elemBase.getListInstance()){
                int idElemAux = (int)dataElemBase.getElementAtPos(posTagIDTag);
                int rating = (int)dataElemBase.getElementAtPos(posTagRate);
                PosicionElementoRating dat = new PosicionElementoRating(idElemAux,rating);
                heapElemBase.add(dat);
            }
            //heap del elemento a comparar
            //Instances elemComp = instanciasDataInfo.getListInstancesWhereColumnEquals(TagIDElem, identificadorElem);
            //instanciasDataInfo = instanciasDataInfo.getListInstancesWhereColumnDistinct(TagIDElem, identificadorElem);
            Instances elemComp = this.InstancesDeIDElem.get(identificadorElem);
            PriorityQueue<PosicionElementoRating> heapElemComp = new PriorityQueue();
            for(Instance dataElemComp : elemComp.getListInstance()){
                int idElemAux = (int)dataElemComp.getElementAtPos(posTagIDTag);
                int rating = (int)dataElemComp.getElementAtPos(posTagRate);
                PosicionElementoRating dat = new PosicionElementoRating(idElemAux,rating);
                heapElemComp.add(dat);
            }
            //calcular coseno == medida de similitud
            double sim = this.calculaCoseno(heapElemBase, heapElemComp);
            MemSimilitud mem = new MemSimilitud(identificadorElem, sim);
            this.similitudes.add(mem);
        }
        
        
        //obtenemos solo los K mejores cosenos, pero solo si el usuario los ha votado
        ArrayList<MemSimilitud> mejoresCosenos = new ArrayList<>();
        int i = 0;
        while(!this.similitudes.isEmpty()){
            if(i >= this.K){
                break;
            }
            MemSimilitud mem = this.similitudes.poll();
            //System.out.println(mem.getId() + "\t" + mem.getSimilitud());
            int idElemAux = mem.getId();
            if(informacionUsuario.getListInstancesWhereColumnEquals(TagIDElem, idElemAux).isEmpty()){
                
            }else{
                mejoresCosenos.add(mem);
                i++;
            }
        }
        
         //conseguimos el rating dado un item
        AuxObtenerRating ratingElem = new AuxObtenerRating();
        for(MemSimilitud mem : mejoresCosenos){
            int elemID = mem.getId();
            double similitud = mem.getSimilitud();
            
            //ha de tener solo una instancia
            Instances inst = informacionUsuario.getListInstancesWhereColumnEquals(TagIDElem, elemID);
            Instance instance = inst.getInstanceAtPos(0);
            double rating = (double) instance.getElementAtPos(posRatingRated);
            System.out.println(elemID + "\t" + similitud +"\t" + rating);
            ratingElem.addElement(rating, similitud);
        }
        Recomendacion rec = new Recomendacion(idElem, ratingElem.getRating());
        recomendacion.add(rec);
        return recomendacion;
    }
    private double calculaCoseno(PriorityQueue<PosicionElementoRating> heapURS, PriorityQueue<PosicionElementoRating> heapOtro){
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
