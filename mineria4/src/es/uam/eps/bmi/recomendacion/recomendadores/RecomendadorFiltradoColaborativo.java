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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.collections15.map.FastHashMap;

/**
 *
 * @author dani
 */
public class RecomendadorFiltradoColaborativo {
    private int K = 20;
    private int incidenciasMinimo = 0;
    private PriorityQueue<MemSimilitud> similitudes;
    private HashMap<Integer, Instances> InstancesDeIDElem = null;
    private ArrayList<Integer> idsDiferentesUsuarios = null;
    
    public RecomendadorFiltradoColaborativo(){
        
    }
    
    public List<Recomendacion> recomiendaUsuario(String TagUsuario,String TagRating,String TagIDElem, int idUsuario, Instances instancias){
        ArrayList<Recomendacion> recomendacion = new ArrayList<>();
        this.similitudes = new PriorityQueue();
        if(this.idsDiferentesUsuarios == null){
            this.idsDiferentesUsuarios = instancias.getListaIDNoRepetidosColumna(TagUsuario);
        }
        
        if(this.InstancesDeIDElem == null){
            this.InstancesDeIDElem = new FastHashMap<>();
            for(int identificadorUsuarioAux:idsDiferentesUsuarios){
                this.InstancesDeIDElem.put(identificadorUsuarioAux, instancias.getListInstancesWhereColumnEquals(TagUsuario, identificadorUsuarioAux));
                //instancias = instancias.getListInstancesWhereColumnDistinct(TagUsuario, identificadorUsuarioAux);
            }
        }
        
        //cogemos las instancias solo de un usuario dado
        //Instances informacionUsuario = instancias.getListInstancesWhereColumnEquals(TagUsuario, idUsuario);
        Instances informacionUsuario = this.InstancesDeIDElem.get(idUsuario);
        if(informacionUsuario.nInstances() <= 0){
            return recomendacion;
        }
        //limpiamos del usuario objetivo las instancias ocultas
        informacionUsuario = informacionUsuario.getInstancesNoOcultas();
        if(informacionUsuario.nInstances() <= 0){
            return recomendacion;
        }
        //cogemos las instancias que no son de un usuario
        /*Instances informacionNoUsuario = instancias.getListInstancesWhereColumnDistinct(TagUsuario, idUsuario);
        if(informacionNoUsuario.nInstances() <= 0){
            return recomendacion;
        }*/
        int posInformacionUsario = instancias.getPosFromColumn(TagUsuario);
        int posRating = instancias.getPosFromColumn(TagRating);
        int posIDElem = instancias.getPosFromColumn(TagIDElem);
        
        SortedSet<PosicionElementoRating> sortedDataUSR = new TreeSet();
        for(Instance dataUSR : informacionUsuario.getListInstance()){
            int idElem = (int)dataUSR.getElementAtPos(posIDElem);
            double rating = (double)dataUSR.getElementAtPos(posRating);
            PosicionElementoRating dat = new PosicionElementoRating(idElem,rating);
            sortedDataUSR.add(dat);
        }
        for(int idUsuarioCompara:idsDiferentesUsuarios){
            //conseguimos el user id de una instancia, para seguir un orden usamos la del primer usuario siguente
            //Instance primeraInstancia = informacionNoUsuario.getInstanceAtPos(0);
            //int idUsuarioCompara = (int)primeraInstancia.getElementAtPos(posInformacionUsario);
            //conseguimos la informacion de ese usuario y la eliminamos de la lista
            //Instances informacionUsuarioCompara = instancias.getListInstancesWhereColumnEquals(TagUsuario, idUsuarioCompara);7
            Instances informacionUsuarioCompara = this.InstancesDeIDElem.get(idUsuarioCompara);
            //limpiamos las filas ocultas del usuario
            informacionUsuarioCompara = informacionUsuarioCompara.getInstancesNoOcultas();
            //informacionNoUsuario = informacionNoUsuario.getListInstancesWhereColumnDistinct(TagUsuario, idUsuarioCompara);
            //generamos tuplas elemento de voto y valoracion, y se anaden por cada usuario en un Heap
            //tenemos que usar PosicionElementoRating por la ordenacion
            PriorityQueue<PosicionElementoRating> heapUSR = new PriorityQueue(sortedDataUSR);
            /*for(Instance dataUSR : informacionUsuario.getListInstance()){
                int idElem = (int)dataUSR.getElementAtPos(posIDElem);
                double rating = (double)dataUSR.getElementAtPos(posRating);
                PosicionElementoRating dat = new PosicionElementoRating(idElem,rating);
                heapUSR.add(dat);
            }*/
            //heap del usuario a comparar
            PriorityQueue<PosicionElementoRating> heapOtro = new PriorityQueue();
            for(Instance dataUSR : informacionUsuarioCompara.getListInstance()){
                int idElem = (int)dataUSR.getElementAtPos(posIDElem);
                double rating = (double)dataUSR.getElementAtPos(posRating);
                PosicionElementoRating dat = new PosicionElementoRating(idElem,rating);
                heapOtro.add(dat);
            }
            //calcular coseno == medida de similitud
            double sim = this.calculaCoseno(heapUSR, heapOtro);
            MemSimilitud mem = new MemSimilitud(idUsuarioCompara, sim);
            this.similitudes.add(mem);
        }
        //obtenemos solo los K mejores cosenos
        ArrayList<MemSimilitud> mejoresCosenos = new ArrayList<>();
        for(int i = 0; i < this.K; i++){
            MemSimilitud mem = this.similitudes.poll();
            mejoresCosenos.add(mem);
        }
        //conseguimos el rating dado un item
        HashMap<Integer, AuxObtenerRating> hasmGetRatingFinal = new HashMap<>();
        for(MemSimilitud mem : mejoresCosenos){
            int userID = mem.getId();
            double similitud = mem.getSimilitud();
            //Instances informacionIsuarioParecido = instancias.getListInstancesWhereColumnEquals(TagUsuario, userID);
            Instances informacionIsuarioParecido = this.InstancesDeIDElem.get(userID);
            for(Instance inst : informacionIsuarioParecido.getListInstance()){
                int idElm = (int)inst.getElementAtPos(posIDElem);
                double rat = (double)inst.getElementAtPos(posRating);
                if(hasmGetRatingFinal.containsKey(idElm)){
                    AuxObtenerRating auxR = hasmGetRatingFinal.get(idElm);
                    auxR.addElement(rat, similitud);
                }else{
                    AuxObtenerRating auxR = new AuxObtenerRating();
                    auxR.addElement(rat, similitud);
                    hasmGetRatingFinal.put(idElm, auxR);
                }
            }
        }
        //obtenemos los mejores ratings
        //
        for(Integer elemID: hasmGetRatingFinal.keySet()){
            AuxObtenerRating auxR = hasmGetRatingFinal.get(elemID);
            int incedencias = auxR.getIncidencias();
            
            if(incedencias > this.incidenciasMinimo){
                //debemos eliminar los elementos que el usuario ya ha votado
                Instances usuarioTieneElem = informacionUsuario.getListInstancesWhereColumnEquals(TagIDElem, elemID);
                if(usuarioTieneElem.isEmpty()){
                    double puntuacion = auxR.getRating();
                    Recomendacion rec = new Recomendacion(elemID, puntuacion);
                    recomendacion.add(rec);
                }
                
            }
            
        }
        //debemos eliminar los elementos que el usuario ya ha votado
        
        Collections.sort(recomendacion);
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
