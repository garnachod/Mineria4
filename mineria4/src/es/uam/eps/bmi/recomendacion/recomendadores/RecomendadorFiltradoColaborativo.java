/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.recomendadores;

import es.uam.eps.bmi.recomendacion.datos.Instances;
import es.uam.eps.bmi.recomendacion.datos.Recomendacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dani
 */
public class RecomendadorFiltradoColaborativo {
    private int K = 10;
    
    public List<Recomendacion> recomiendaUsuario(String TagUsuario, int idUsuario, Instances instancias){
        ArrayList<Recomendacion> recomendacion = new ArrayList<>();
        //cogemos las instancias solo de un usuario dado
        Instances informacionUsuario = instancias.getListInstancesWhereColumnEquals(TagUsuario, idUsuario);
        if(informacionUsuario.nInstances() <= 0){
            return recomendacion;
        }
        //cogemos las instancias que no son de un usuario
        Instances informacionNoUsuario = instancias.getListInstancesWhereColumnDistinct(TagUsuario, idUsuario);
        if(informacionNoUsuario.nInstances() <= 0){
            return recomendacion;
        }
        return recomendacion;
    }
}
