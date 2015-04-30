
package es.uam.eps.bmi.recomendacion.lectores;

import es.uam.eps.bmi.recomendacion.datos.Instances;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * @author Diego Castaño y Daniel Garnacho
 * 
 * Objeto generico de lectores de ficheros de datos.
 */
public abstract class LectorInstances {
    /**
     * Lee un fichero dado su nombre y retorna un objeto Instance inicializado con las instancias que contiene el fichero
     * @param nombreFichero
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public abstract Instances leeFichero(String nombreFichero) throws FileNotFoundException, IOException;
}
