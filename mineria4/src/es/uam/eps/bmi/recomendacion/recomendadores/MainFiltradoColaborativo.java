/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.recomendadores;

import es.uam.eps.bmi.recomendacion.datos.Instances;
import es.uam.eps.bmi.recomendacion.datos.Recomendacion;
import es.uam.eps.bmi.recomendacion.lectores.LectorUserRated;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dani
 */
public class MainFiltradoColaborativo {
    public static void main(String[] args) throws Exception{
        LectorUserRated lector = new LectorUserRated();
        Instances instancias = lector.leeFichero("./src/user_ratedmovies.dat");
        RecomendadorFiltradoColaborativo recomendador = new RecomendadorFiltradoColaborativo();
        int idUsuario = 75;
        List<Recomendacion> recomendaciones = recomendador.recomiendaUsuario("userID", "rating", "movieID", idUsuario, instancias);
        
        System.out.println("Recomendaciones");
        for(int i=0; i< 20; i++){
            Recomendacion r = recomendaciones.get(i);
            System.out.println("movieID = "+r.getIdElemRecom() +"\t"+ r.getPuntuacion());
        }
    }
}
