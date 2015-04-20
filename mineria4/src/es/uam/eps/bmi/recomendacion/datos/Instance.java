/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.datos;

import java.util.ArrayList;


public class Instance {
    private ArrayList<Object> listaDatos;
    private boolean oculto = false;
    
    public Instance(){
        this.listaDatos = new ArrayList();
    }
    
    public void setOculto(boolean oculto){
        this.oculto = oculto;
    }
    
    public boolean isOculto(){
        return this.oculto;
    }
    
    public void addElement(Object element){
        this.listaDatos.add(element);
    }
    
    public Object getElementAtPos(int pos){
        return this.listaDatos.get(pos);
    }
}
