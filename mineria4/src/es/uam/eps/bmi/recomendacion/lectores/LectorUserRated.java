/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.lectores;

import es.uam.eps.bmi.recomendacion.datos.Instance;
import es.uam.eps.bmi.recomendacion.datos.Instances;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * @author Diego Castaño y Daniel Garnacho
 * 
 */
public class LectorUserRated extends LectorInstances{
    /**
     * Lector del fichero UserRated
     * @param nombreFichero
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    @Override
    public Instances leeFichero(String nombreFichero) throws FileNotFoundException, IOException{
        BufferedReader in = new BufferedReader(new FileReader(new File(nombreFichero)));
        Instances instances = new Instances();
        
        //primera linea columnas
        String columnasLinea = in.readLine();
        String [] columnas = columnasLinea.split("\t");
        for(String columna:columnas){
            instances.addColumn(columna);
        }
        //resto de lineas, instancias
        while(in.ready()){
            String cadenaInstancia = in.readLine();
            columnas = cadenaInstancia.split("\t");
            
            if(columnas.length <= 1){
                break;
            }
            
            Instance instance = new Instance();
            int i = 0;
            for(String columna:columnas){
                if(i == 2){
                    instance.addElement(Double.parseDouble(columna));
                }else{
                    instance.addElement(Integer.parseInt(columna));
                }
                i++;
            }
            instances.addInstance(instance);
        }
        in.close();
        return instances;
    }
}
