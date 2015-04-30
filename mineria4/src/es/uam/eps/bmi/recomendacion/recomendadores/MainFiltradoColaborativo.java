package es.uam.eps.bmi.recomendacion.recomendadores;

import es.uam.eps.bmi.recomendacion.datos.Instance;
import es.uam.eps.bmi.recomendacion.datos.Instances;
import es.uam.eps.bmi.recomendacion.datos.Recomendacion;
import es.uam.eps.bmi.recomendacion.lectores.LectorUserRated;
import java.util.List;
import java.util.Scanner;

/**
 * @author Diego Castaño y Daniel Garnacho
 * 
 */
public class MainFiltradoColaborativo {
    public static void main(String[] args) throws Exception{
        LectorUserRated lector = new LectorUserRated();
        Instances instancias = lector.leeFichero("./src/user_ratedmovies.dat");
        RecomendadorFiltradoColaborativo recomendador = new RecomendadorFiltradoColaborativo();
        
        System.out.println("Introduce un Id de usuario: ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int idUsuario = Integer.parseInt(s);
        // id de usuario obtenido
        //imprimimos sus ratings
        Instances userINST = instancias.getListInstancesWhereColumnEquals("userID", idUsuario);
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
        List<Recomendacion> recomendaciones = recomendador.recomiendaUsuario("userID", "rating", "movieID", idUsuario, instancias);
        time_end = System.currentTimeMillis(); 
        System.out.println("the task has taken " + ( time_end - time_start )/1000 + " seconds");
        
        System.out.println("Recomendaciones:\nmovieID\trating");
        for(int i=0; i< recomendaciones.size() && i < 50; i++){
            Recomendacion r = recomendaciones.get(i);
            System.out.println(r.getIdElemRecom() + "\t"+ r.getPuntuacion());
        }
    }
}
