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
import java.util.Random;

/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class MainEvaluacionFiltradoColaborativo {
    private double sumaDiferenciasCuadrado = 0.0;
    private double sumaDiferencias = 0.0;
    private int nInstancesOcutasRMSE = 0;
    private int nInstancesOcutasMAE = 0;
    
    public static void main(String[] args) throws Exception{
        LectorUserRated lector = new LectorUserRated();
        Instances instancias = lector.leeFichero("./src/user_ratedmovies.dat");
        RecomendadorFiltradoColaborativo recomendador = new RecomendadorFiltradoColaborativo();
        
        //ocultar Instancias
        MainEvaluacionFiltradoColaborativo.ocultaInstancias(0.20,instancias);
        //test de ocultacion
        //System.out.println(instancias.getInstancesOcultas().nInstances());
        
        //
        ArrayList<Integer> idsUser = instancias.getListaIDNoRepetidosColumna("userID");
        MainEvaluacionFiltradoColaborativo evaluacion = new MainEvaluacionFiltradoColaborativo();
        for(int idUsuario:idsUser){
            //System.out.println(idUsuario);
            List<Recomendacion> recomendaciones = recomendador.recomiendaUsuario("userID", "rating", "movieID", idUsuario, instancias);
            evaluacion.calculaMAEParcial("userID", "rating", "movieID", idUsuario, instancias, recomendaciones);
            evaluacion.calculaRMSEParcial("userID", "rating", "movieID", idUsuario, instancias, recomendaciones);
        }
        
        System.out.println("MAE es: " + evaluacion.calculaMAEFinal());
        System.out.println("RMSE es: " + evaluacion.calculaRMSEFinal());
        
    }
    public void calculaMAEParcial(String TagUsuario,String TagRating,String TagIDElem, int idUsuario, Instances instancias, List<Recomendacion> recomendaciones){
        double sumaDiferencias = 0.0;
        //cogemos las instancias solo de un usuario dado
        Instances informacionUsuario = instancias.getListInstancesWhereColumnEquals(TagUsuario, idUsuario);
        if(informacionUsuario.nInstances() <= 0){
            return;
        }
        //limpiamos del usuario objetivo las instancias no ocultas
        informacionUsuario = informacionUsuario.getInstancesOcultas();
        if(informacionUsuario.nInstances() <= 0){
            return;
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
        this.nInstancesOcutasMAE += informacionUsuario.nInstances();
        this.sumaDiferencias += sumaDiferencias;
        //return (1.0/informacionUsuario.nInstances())*sumaDiferencias;
    }
    public double calculaMAEFinal(){
        return (1.0/this.nInstancesOcutasMAE)*sumaDiferencias;
    }
    public void calculaRMSEParcial(String TagUsuario,String TagRating,String TagIDElem, int idUsuario, Instances instancias, List<Recomendacion> recomendaciones){
        double sumaDiferenciasCuadrado = 0.0;
        //cogemos las instancias solo de un usuario dado
        Instances informacionUsuario = instancias.getListInstancesWhereColumnEquals(TagUsuario, idUsuario);
        if(informacionUsuario.nInstances() <= 0){
            return;
        }
        //limpiamos del usuario objetivo las instancias no ocultas
        informacionUsuario = informacionUsuario.getInstancesOcultas();
        if(informacionUsuario.nInstances() <= 0){
            return;
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
        this.nInstancesOcutasRMSE += informacionUsuario.nInstances();
        this.sumaDiferenciasCuadrado += sumaDiferenciasCuadrado;
        //return Math.sqrt((1.0/informacionUsuario.nInstances())*sumaDiferenciasCuadrado);
    }
    public double calculaRMSEFinal(){
        return Math.sqrt((1.0/this.nInstancesOcutasRMSE)*this.sumaDiferenciasCuadrado);
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
