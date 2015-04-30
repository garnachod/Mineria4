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
 * @author Diego Castaño y Daniel Garnacho
 * Recomendador filtrado colaborativo
 */
public class RecomendadorFiltradoColaborativo extends Recomendador{
    private int K = 25;
    private int incidenciasMinimo = 1;
    private HashMap<Integer, Instances> InstancesDeIDElem = null;
    private ArrayList<Integer> idsDiferentesUsuarios = null;
    
    public RecomendadorFiltradoColaborativo(){
        
    }
    /**
     * Recomienda una serie de elementos usando el algorimo de filtrado colaborativo
     * @param TagUsuario Nombre de columna que contiene el id de Usuario
     * @param TagRating Nombre de columna que contiene el rating
     * @param TagIDElem Nombre de columna que contiene el id del elemento
     * @param idUsuario usuario a devolver las recomendaciones
     * @param instancias Instancias del fichero Rated movies o similar
     * @return lista de recomendaciones ordenadas por Rating
     */
    public List<Recomendacion> recomiendaUsuario(String TagUsuario,String TagRating,String TagIDElem, int idUsuario, Instances instancias){
        ArrayList<Recomendacion> recomendacion = new ArrayList<>();
        
        if(this.idsDiferentesUsuarios == null){
            this.idsDiferentesUsuarios = instancias.getListaIDNoRepetidosColumna(TagUsuario);
        }
        //si no está generado el hash de informacion de genera
        if(this.InstancesDeIDElem == null){
            this.InstancesDeIDElem = new FastHashMap<>();
            for(int identificadorUsuarioAux:idsDiferentesUsuarios){
                this.InstancesDeIDElem.put(identificadorUsuarioAux, instancias.getListInstancesWhereColumnEquals(TagUsuario, identificadorUsuarioAux).getInstancesNoOcultas());
                //instancias = instancias.getListInstancesWhereColumnDistinct(TagUsuario, identificadorUsuarioAux);
            }
        }
        
        //cogemos las instancias solo de un usuario dado
        Instances informacionUsuario = this.InstancesDeIDElem.get(idUsuario);
        if(informacionUsuario.nInstances() <= 0){
            return recomendacion;
        }
        //limpiamos del usuario objetivo las instancias ocultas
        /*informacionUsuario = informacionUsuario.getInstancesNoOcultas();
        if(informacionUsuario.nInstances() <= 0){
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
        PriorityQueue<MemSimilitud> similitudes = new PriorityQueue();
        for(int idUsuarioCompara:idsDiferentesUsuarios){
            if(idUsuarioCompara == idUsuario){
                continue;
            }
            //heap del usuario
            PriorityQueue<PosicionElementoRating> heapUSR = new PriorityQueue(sortedDataUSR);
            //instancias del usuario a comparar
            Instances informacionUsuarioCompara = this.InstancesDeIDElem.get(idUsuarioCompara);
            //limpiamos las filas ocultas del usuario
            //informacionUsuarioCompara = informacionUsuarioCompara.getInstancesNoOcultas();
            //heap del usuario a comparar
            PriorityQueue<PosicionElementoRating> heapOtro = new PriorityQueue();
            for(Instance dataUSR : informacionUsuarioCompara.getListInstance()){
                int idElem = (int)dataUSR.getElementAtPos(posIDElem);
                double rating = (double)dataUSR.getElementAtPos(posRating);
                PosicionElementoRating dat = new PosicionElementoRating(idElem,rating);
                heapOtro.add(dat);
            }
            //calcular coseno == medida de similitud
            double sim = Similitud.coseno(heapUSR, heapOtro);
            MemSimilitud mem = new MemSimilitud(idUsuarioCompara, sim);
            similitudes.add(mem);
        }
        //obtenemos solo los K mejores cosenos
        ArrayList<MemSimilitud> mejoresCosenos = new ArrayList<>();
        for(int i = 0; i < this.K; i++){
            MemSimilitud mem = similitudes.poll();
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
            
            if(incedencias >= this.incidenciasMinimo){
                //debemos eliminar los elementos que el usuario ya ha votado
                Instances usuarioTieneElem = informacionUsuario.getListInstancesWhereColumnEquals(TagIDElem, elemID);
                if(usuarioTieneElem.isEmpty()){
                    double puntuacion = auxR.getRating();
                    Recomendacion rec = new Recomendacion(elemID, puntuacion);
                    recomendacion.add(rec);
                }
                
            }
            
        }
        
        Collections.sort(recomendacion);
        return recomendacion;
    }
}
