/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.recomendadores;

import es.uam.eps.bmi.recomendacion.datos.Instance;
import es.uam.eps.bmi.recomendacion.datos.Instances;
import es.uam.eps.bmi.recomendacion.datos.Recomendacion;
import es.uam.eps.bmi.recomendacion.lectores.LectorUserRated;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 *
 * @author dani
 */
public class MainEvaluacionFiltradoColaborativo {
    public static void main(String[] args) throws Exception{
        LectorUserRated lector = new LectorUserRated();
        Instances instancias = lector.leeFichero("./src/user_ratedmovies.dat");
        RecomendadorFiltradoColaborativo recomendador = new RecomendadorFiltradoColaborativo();
        
        //ocultar Instancias
        MainEvaluacionFiltradoColaborativo.ocultaInstancias(0.20,instancias);
        //test de ocultacion
        //System.out.println(instancias.getInstancesOcultas().nInstances());
        
        //
        double mae = 0;
        double rmse = 0;
        ArrayList<Integer> idsUser = instancias.getListaIDNoRepetidosColumna("userID");
        for(int idUsuario:idsUser){
            System.out.println(idUsuario);
            List<Recomendacion> recomendaciones = recomendador.recomiendaUsuario("userID", "rating", "movieID", idUsuario, instancias);
            mae += MainEvaluacionFiltradoColaborativo.calculaMAE("userID", "rating", "movieID", idUsuario, instancias, recomendaciones);
            rmse += MainEvaluacionFiltradoColaborativo.calculaRMSE("userID", "rating", "movieID", idUsuario, instancias, recomendaciones);
        }
        
        System.out.println("MAE es: " + mae/idsUser.size());
        System.out.println("RMSE es: " + rmse/idsUser.size());
        
    }
    public static double calculaMAE(String TagUsuario,String TagRating,String TagIDElem, int idUsuario, Instances instancias, List<Recomendacion> recomendaciones){
        double sumaDiferencias = 0.0;
        //cogemos las instancias solo de un usuario dado
        Instances informacionUsuario = instancias.getListInstancesWhereColumnEquals(TagUsuario, idUsuario);
        if(informacionUsuario.nInstances() <= 0){
            return 0;
        }
        //limpiamos del usuario objetivo las instancias no ocultas
        informacionUsuario = informacionUsuario.getInstancesOcultas();
        if(informacionUsuario.nInstances() <= 0){
            return 0;
        }
        //System.out.println("Instancias Test: " + informacionUsuario.nInstances());
        int posRating = instancias.getPosFromColumn(TagRating);
        int posIDElem = instancias.getPosFromColumn(TagIDElem);
        for(Instance inst : informacionUsuario.getListInstance()){
            boolean flag = false;
            int idElem = (int)inst.getElementAtPos(posIDElem);
            double rating = (double)inst.getElementAtPos(posRating);
            for(Recomendacion r:recomendaciones){
                if(r.getIdElemRecom() == idElem){
                    sumaDiferencias += Math.abs(r.getPuntuacion() - rating);
                    flag = true;
                    break;
                }
            }
            if(flag == false){
                //no se ha podido recomendar esa pelicula
                sumaDiferencias += Math.abs(0.0 - rating);
            }
        }
        
        return (1.0/informacionUsuario.nInstances())*sumaDiferencias;
    }
    public static double calculaRMSE(String TagUsuario,String TagRating,String TagIDElem, int idUsuario, Instances instancias, List<Recomendacion> recomendaciones){
        double sumaDiferenciasCuadrado = 0.0;
        //cogemos las instancias solo de un usuario dado
        Instances informacionUsuario = instancias.getListInstancesWhereColumnEquals(TagUsuario, idUsuario);
        if(informacionUsuario.nInstances() <= 0){
            return 0;
        }
        //limpiamos del usuario objetivo las instancias no ocultas
        informacionUsuario = informacionUsuario.getInstancesOcultas();
        if(informacionUsuario.nInstances() <= 0){
            return 0;
        }
        //System.out.println("Instancias Test: " + informacionUsuario.nInstances());
        int posRating = instancias.getPosFromColumn(TagRating);
        int posIDElem = instancias.getPosFromColumn(TagIDElem);
        for(Instance inst : informacionUsuario.getListInstance()){
            boolean flag = false;
            int idElem = (int)inst.getElementAtPos(posIDElem);
            double rating = (double)inst.getElementAtPos(posRating);
            for(Recomendacion r:recomendaciones){
                if(r.getIdElemRecom() == idElem){
                    sumaDiferenciasCuadrado += Math.pow(r.getPuntuacion() - rating, 2);
                    flag = true;
                    break;
                }
            }
            if(flag == false){
                //no se ha podido recomendar esa pelicula
                sumaDiferenciasCuadrado += Math.pow(0.0 - rating, 2);
            }
        }
        
        return Math.sqrt((1.0/informacionUsuario.nInstances())*sumaDiferenciasCuadrado);
    }
    public static void ocultaInstancias(double porcentajeOcultas, Instances instancias){
        Random  rnd = new Random();
        
        for(Instance inst : instancias.getListInstance()){
            if(rnd.nextDouble() <= porcentajeOcultas){
                inst.setOculto(true);
            }
        }
    }
}
