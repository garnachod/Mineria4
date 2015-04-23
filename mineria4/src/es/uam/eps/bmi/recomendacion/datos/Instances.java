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
    
    public int nInstances(){
        return this.listaDeInstance.size();
    }
    public boolean isEmpty(){
        return this.nInstances() <= 0;
    }
    
    public int getPosFromColumn(String nColumna){
        if(this.ColumnPosition.containsKey(nColumna)){
            return this.ColumnPosition.get(nColumna);
        }
        return -1;
    }
    public Instance getInstanceAtPos(int pos){
        return this.listaDeInstance.get(pos);
    }
    public void addColumn(String column){
        int pos = this.Columns.size();
        this.ColumnPosition.put(column, pos);
        this.Columns.add(column);
    }
    
    public ArrayList<Instance> getListInstance(){
        return this.listaDeInstance;
    }
    private Instances getInstanceInicializadaColumnas(){
        Instances retorno = new Instances();
        for(String columna: this.Columns){
            retorno.addColumn(columna);
        }
        return retorno;
    }
    
    //filtrado
    public Instances getInstancesWhereColumnEquals(String column, String data){
        Instances retorno = this.getInstanceInicializadaColumnas();
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            if(data.equals((String)inst.getElementAtPos(pos))){
                retorno.addInstance(inst);
            }
            
        }
        return retorno;
    }
    public Instances getListInstancesWhereColumnEquals(String column, double data){
        Instances retorno = this.getInstanceInicializadaColumnas();
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            if(data == (double)inst.getElementAtPos(pos)){
                retorno.addInstance(inst);
            }
            
        }
        return retorno;
    }
    public Instances getListInstancesWhereColumnEquals(String column, int data){
        Instances retorno = this.getInstanceInicializadaColumnas();
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            int dataComp = (int)inst.getElementAtPos(pos);
            if(data == dataComp){
                retorno.addInstance(inst);
            }
            //suponemos los datos ordenados, se ganan unos 12 segundos
            if(data < dataComp){
                break;
            }
        }
        return retorno;
    }
    public Instances getListInstancesWhereColumnDistinct(String column, String data){
        Instances retorno = this.getInstanceInicializadaColumnas();
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            if(!data.equals((String)inst.getElementAtPos(pos))){
                retorno.addInstance(inst);
            }
        }
        return retorno;
    }
    
    public Instances getListInstancesWhereColumnDistinct(String column, double data){
        Instances retorno = this.getInstanceInicializadaColumnas();
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            if(data != (double)inst.getElementAtPos(pos)){
                retorno.addInstance(inst);
            }
        }
        return retorno;
    }
    
    public Instances getListInstancesWhereColumnDistinct(String column, int data){
        Instances retorno = this.getInstanceInicializadaColumnas();
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            if(data != (int)inst.getElementAtPos(pos)){
                retorno.addInstance(inst);
            }
        }
        return retorno;
    }
    
    public ArrayList<Integer> getListaIDNoRepetidosColumna(String column){
        HashMap<Integer, Boolean> hashNoRep = new HashMap<Integer, Boolean>();
        int pos = this.ColumnPosition.get(column);
        for(Instance inst : this.listaDeInstance){
            if(hashNoRep.containsKey(inst.getElementAtPos(pos))){
                
            }else{
                hashNoRep.put((int)inst.getElementAtPos(pos), Boolean.TRUE);
            }
        }
        ArrayList<Integer> retorno = new ArrayList<>(hashNoRep.keySet());
        return retorno;
    }
    public Instances getInstancesNoOcultas(){
        Instances retorno = this.getInstanceInicializadaColumnas();
        for(Instance inst : this.listaDeInstance){
            if(!inst.isOculto()){
                retorno.addInstance(inst);
            }
        }
         return retorno;
    }
     public Instances getInstancesOcultas(){
        Instances retorno = this.getInstanceInicializadaColumnas();
        for(Instance inst : this.listaDeInstance){
            if(inst.isOculto()){
                retorno.addInstance(inst);
            }
        }
        return retorno;
    }
}
