/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.recomendadores;

import es.uam.eps.bmi.recomendacion.datos.Instances;
import es.uam.eps.bmi.recomendacion.datos.Recomendacion;
import es.uam.eps.bmi.recomendacion.lectores.LectorMovieTags;
import es.uam.eps.bmi.recomendacion.lectores.LectorUserRated;
import java.util.List;

/**
 *
 * @author dani
 */
public class MainBasadoContenido {
    public static void main(String[] args) throws Exception{
        LectorUserRated lectorRated = new LectorUserRated();
        Instances instanciasRated = lectorRated.leeFichero("./src/user_ratedmovies.dat");
        LectorMovieTags lectorTags = new LectorMovieTags();
        Instances instanciasTags = lectorTags.leeFichero("./src/movie_tags.dat");
        
        RecomendadorBasadoContenido recomendador = new RecomendadorBasadoContenido();
        int idUsuario = 78;
        int idElem = 110;
        System.out.println("Buscando...");
        List<Recomendacion> recomendaciones = recomendador.recomiendaUsuarioElem("userID", "rating", "movieID", "tagID", "tagWeight", idUsuario, idElem, instanciasRated, instanciasTags);
        
        System.out.println("Recomendaciones");
        for(int i=0; i< 1; i++){
            Recomendacion r = recomendaciones.get(i);
            System.out.println("movieID = "+r.getIdElemRecom() +"\t"+ r.getPuntuacion());
        }
    }
}
