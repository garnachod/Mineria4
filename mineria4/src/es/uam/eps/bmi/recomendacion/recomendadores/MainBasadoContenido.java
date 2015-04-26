/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.recomendadores;

import es.uam.eps.bmi.recomendacion.datos.Instance;
import es.uam.eps.bmi.recomendacion.datos.Instances;
import es.uam.eps.bmi.recomendacion.datos.Recomendacion;
import es.uam.eps.bmi.recomendacion.lectores.LectorMovieTags;
import es.uam.eps.bmi.recomendacion.lectores.LectorUserRated;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("Introduce un Id de usuario: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int idUsuario = Integer.parseInt(s);
        // id de usuario obtenido
        //imprimimos sus ratings
        Instances userINST = instanciasRated.getListInstancesWhereColumnEquals("userID", idUsuario);
        //puede que no exista
        if(userINST.isEmpty()){
            System.out.println("No existe el usuario");
            return;
        }
        //
        System.out.println("Usuario "+idUsuario+": \nmovieID\trating");
        for(Instance inst:userINST.getListInstance()){
            System.out.println(inst.getElementAtPos(1)+"\t"+inst.getElementAtPos(2));
        }
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        List<Recomendacion> recomendaciones = recomendador.recomiendaUsuario("userID", "rating", "movieID", "tagID", "tagWeight", idUsuario, instanciasRated, instanciasTags);
        time_end = System.currentTimeMillis(); 
        System.out.println("the task has taken " + ( time_end - time_start )/1000 + " seconds");
        //se imprimen los resultados
        System.out.println("Recomendaciones:\nmovieID\trating");
        for(int i=0; i< recomendaciones.size() && i < 50; i++){
            Recomendacion r = recomendaciones.get(i);
            System.out.println(r.getIdElemRecom() + "\t"+ r.getPuntuacion());
        }
    }
}
