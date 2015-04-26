/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.lectores;

import es.uam.eps.bmi.recomendacion.datos.Instances;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author dani
 */
public abstract class LectorInstances {
    public abstract Instances leeFichero(String nombreFichero) throws FileNotFoundException, IOException;
}
