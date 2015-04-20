/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.recomendacion.datos;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author dani
 */
public class Instances {
    private ArrayList<Instance> listaDeInstance;
    private ArrayList<String> Columns;
    private HashMap<String, Integer> ColumnPosition;
    
    public Instances(){
        this.listaDeInstance = new ArrayList<>();
        this.Columns = new ArrayList<>();
        this.ColumnPosition = new HashMap<>();
    }
    public void addInstance(Instance inst){
        this.listaDeInstance.add(inst);
    }
    
    public void addColumn(String column){
        int pos = this.Columns.size();
        this.ColumnPosition.put(column, pos);
        this.Columns.add(column);
    }
    
    public ArrayList<Instance> getListInstance(){
        return this.listaDeInstance;
    }
    //filtrado
    public Instances getInstancesWhereColumnEquals(String column, String data){
        Instances retorno = new Instances();
        for(String columna: this.Columns){
            retorno.addColumn(columna);
        }
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            if(data.equals((String)inst.getElementAtPos(pos))){
                retorno.addInstance(inst);
            }
        }
        return retorno;
    }
    public Instances getListInstancesWhereColumnEquals(String column, double data){
        Instances retorno = new Instances();
        for(String columna: this.Columns){
            retorno.addColumn(columna);
        }
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            if(data == (double)inst.getElementAtPos(pos)){
                retorno.addInstance(inst);
            }
        }
        return retorno;
    }
    public Instances getListInstancesWhereColumnEquals(String column, int data){
        Instances retorno = new Instances();
        for(String columna: this.Columns){
            retorno.addColumn(columna);
        }
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            if(data == (int)inst.getElementAtPos(pos)){
                retorno.addInstance(inst);
            }
        }
        return retorno;
    }
}
