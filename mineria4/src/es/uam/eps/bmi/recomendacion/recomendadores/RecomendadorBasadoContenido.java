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

/**
 * @author Diego Castaño y Daniel Garnacho
 * Recomendador basado en Contenido
 */
public class RecomendadorBasadoContenido extends Recomendador{
    private PriorityQueue<MemSimilitud> similitudes;
    private HashMap<Integer, Instances> InstancesDeIDElem = null;
    private int K = 20;
    private int incidenciasMinimo = 2;
    private ArrayList<Integer> idsElemNoRep = null;
    private HashMap<Integer, HashMap<Integer, Double>> similitudDadaElemento = null;
    /**
     * Recomienda un usuario utilizando un algoritmo de recomendacion basado en contenido
     * 
     * @param TagUsuario Nombre de columna que contiene el id de Usuario
     * @param TagRating Nombre de columna que contiene el rating
     * @param TagIDElem Nombre de columna que contiene el id del elemento
     * @param tagIDStr Nombre de columna que contiene el id de TAG
     * @param tagRate Nombre de columna que contiene el numero de Tags 
     * @param idUsuario usuario a devolver las recomendaciones
     * @param instanciasRated Instancias del fichero Rated movies o similar
     * @param instanciasDataInfo Instancias del fichero Movie Tags o similar
     * @return lista de recomendaciones ordenadas por Rating
     */
    public List<Recomendacion> recomiendaUsuario(String TagUsuario,String TagRating,String TagIDElem, String tagIDStr,String tagRate, int idUsuario, Instances instanciasRated, Instances instanciasDataInfo){
        ArrayList<Recomendacion> recomendacion = new ArrayList<>();
        //cogemos las instancias votadas solo de un usuario dado
        Instances informacionUsuario = instanciasRated.getListInstancesWhereColumnEquals(TagUsuario, idUsuario);
        if(informacionUsuario.nInstances() <= 0){
            return recomendacion;
        }
        if(this.idsElemNoRep==null){
            this.idsElemNoRep = instanciasDataInfo.getListaIDNoRepetidosColumna(TagIDElem);
            //System.out.println(idsElemNoRep.size());
            if(idsElemNoRep.size() <= 0){
                return recomendacion;
            }
        }
        //hay que eliminar los ids ya votados por el usuario
        ArrayList<Integer> idsElemNoRepNoUser = new ArrayList<>();
        for(int idElemento:this.idsElemNoRep){
            if(informacionUsuario.getListInstancesWhereColumnEquals(TagIDElem, idElemento).isEmpty()){
                idsElemNoRepNoUser.add(idElemento);
            }
        }
        //
        for(int idElemento:idsElemNoRepNoUser){
            Recomendacion recomendacionAux = this.recomiendaUsuarioElem(TagUsuario, TagRating, TagIDElem, tagIDStr, tagRate, idUsuario, idElemento, informacionUsuario, instanciasDataInfo);
            if(recomendacionAux != null){
                recomendacion.add(recomendacionAux);
            }
            //System.out.println(idElemento);
        }
        Collections.sort(recomendacion);
        return recomendacion;
    }
    /**
     * Obtiene el rating de un usuario a una pelicula, usuando el algoritmo basado en contenido
     * @param TagUsuario
     * @param TagRating
     * @param TagIDElem
     * @param tagIDStr
     * @param tagRate
     * @param idUsuario
     * @param idElem
     * @param instanciasRated
     * @param instanciasDataInfo
     * @return Rating simulado si se ha podido generar o null si no se ha podido
     */
    public Recomendacion recomiendaUsuarioElem(String TagUsuario,String TagRating,String TagIDElem, String tagIDStr,String tagRate, int idUsuario, int idElem, Instances instanciasRated, Instances instanciasDataInfo){
        Recomendacion recomendacion = null;
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
        
        //buscamos las Instancias de una pelicula
        //para eso primero buscamos los id de pelicula
        if(this.idsElemNoRep==null){
            this.idsElemNoRep = instanciasDataInfo.getListaIDNoRepetidosColumna(TagIDElem);
            if(idsElemNoRep.size() <= 0){
                return recomendacion;
            }
        }
        
        //
        if(this.InstancesDeIDElem == null){
            this.InstancesDeIDElem = new HashMap<>();
            for(int identificador:idsElemNoRep){
                Instances inst = instanciasDataInfo.getListInstancesWhereColumnEquals(TagIDElem, identificador);
                instanciasDataInfo = instanciasDataInfo.getListInstancesWhereColumnDistinct(TagIDElem, identificador);
                this.InstancesDeIDElem.put(identificador, inst);
            } 
        }
        
        Instances elemBase = this.InstancesDeIDElem.get(idElem);
        
        SortedSet<PosicionElementoRating> sortedElemBase = new TreeSet();
        for(Instance dataElemBase : elemBase.getListInstance()){
            int idElemAux = (int)dataElemBase.getElementAtPos(posTagIDTag);
            int rating = (int)dataElemBase.getElementAtPos(posTagRate);
            PosicionElementoRating dat = new PosicionElementoRating(idElemAux,rating);
            sortedElemBase.add(dat);
        }
        ArrayList<Integer> idsCompara = informacionUsuario.getListaIDNoRepetidosColumna(TagIDElem);
        for(int identificadorElem : idsCompara){
            if(identificadorElem == idElem){
                continue;
            }
           
            PriorityQueue<PosicionElementoRating> heapElemBase = new PriorityQueue(sortedElemBase);

            Instances elemComp = this.InstancesDeIDElem.get(identificadorElem);
            PriorityQueue<PosicionElementoRating> heapElemComp = new PriorityQueue();
            for(Instance dataElemComp : elemComp.getListInstance()){
                int idElemAux = (int)dataElemComp.getElementAtPos(posTagIDTag);
                int rating = (int)dataElemComp.getElementAtPos(posTagRate);
                PosicionElementoRating dat = new PosicionElementoRating(idElemAux,rating);
                heapElemComp.add(dat);
            }
            double sim = Similitud.coseno(heapElemBase, heapElemComp);
                
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
            if(mem.getSimilitud() > 0){
                mejoresCosenos.add(mem);
                i++;
            }
            
        }
        
         //conseguimos el rating dado un item
        if(mejoresCosenos.size() >= this.incidenciasMinimo){
            AuxObtenerRating ratingElem = new AuxObtenerRating();
            for(MemSimilitud mem : mejoresCosenos){
                int elemID = mem.getId();
                double similitud = mem.getSimilitud();

                //ha de tener solo una instancia
                Instances inst = informacionUsuario.getListInstancesWhereColumnEquals(TagIDElem, elemID);
                Instance instance = inst.getInstanceAtPos(0);
                double rating = (double) instance.getElementAtPos(posRatingRated);
                //System.out.println(elemID + "\t" + similitud +"\t" + rating);
                ratingElem.addElement(rating, similitud);
            }
            recomendacion = new Recomendacion(idElem, ratingElem.getRating());

            return recomendacion;
        }
        return null;
    }
}
