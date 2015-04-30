package es.uam.eps.bmi.recomendacion.datos;

import java.util.ArrayList;

/**
 * @author Diego Castaño y Daniel Garnacho
 */
public class Instance {
    private ArrayList<Object> listaDatos;
    private boolean oculto = false;
    
    public Instance(){
        this.listaDatos = new ArrayList();
    }
    /**
     * Oculta la instancia para ejercicio 3
     * @param oculto valor true si oculto, false si no oculto
     */
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    /**
     * retorna el valor de la variable oculto
     * @return true si oculto, false si no oculto
     */
    public boolean isOculto(){
        return this.oculto;
    }
    /**
     * Inserta un elemento en el objeto, cada elemento es una columna del fichero de datos
     * @param element Objeto generico a insertar
     */
    public void addElement(Object element){
        this.listaDatos.add(element);
    }
    /**
     * Devuelve una columna de la instancia, dado el número de la columna
     * @param pos posicion de la columna, 1, 2 ...
     * @return Objeto generico en la posicion pos de la instancia
     */
    public Object getElementAtPos(int pos){
        return this.listaDatos.get(pos);
    }
}
