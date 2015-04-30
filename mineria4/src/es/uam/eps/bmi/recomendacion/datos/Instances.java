package es.uam.eps.bmi.recomendacion.datos;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Diego Castaño y Daniel Garnacho
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
    /**
     * Añande una instancia a Instances, objeto contenedor.
     * @param inst Instancia a insertar
     */
    public void addInstance(Instance inst){
        this.listaDeInstance.add(inst);
    }
    /**
     * 
     * @return Número de instancias que contiene el objeto
     */
    public int nInstances(){
        return this.listaDeInstance.size();
    }
    /**
     * 
     * @return True si vacio, false si no
     */
    public boolean isEmpty(){
        return this.nInstances() <= 0;
    }
    /**
     * retorna la posicion de una columna dado su nombre, primera fila de los ficheros de datos.
     * @param nColumna nombre de la columna
     * @return posicion en numero entero
     */
    public int getPosFromColumn(String nColumna){
        if(this.ColumnPosition.containsKey(nColumna)){
            return this.ColumnPosition.get(nColumna);
        }
        return -1;
    }
    /**
     * retorna una instancia en una posicion determinada
     * @param pos posicion de la instancia
     * @return Instancia
     */
    public Instance getInstanceAtPos(int pos){
        return this.listaDeInstance.get(pos);
    }
    /**
     * Añade una definicion de columna, columnas que tiene el fichero de datos
     * @param column Nombre de la columna
     */
    public void addColumn(String column){
        int pos = this.Columns.size();
        this.ColumnPosition.put(column, pos);
        this.Columns.add(column);
    }
    /**
     * Retorna toda la lista de instancias que contenga el objeto
     * @return lista de instancias
     */
    public ArrayList<Instance> getListInstance(){
        return this.listaDeInstance;
    }
    /**
     * retorna una Instances inicializada con las columnas del objeto inicial
     * @return Instances inicializada
     */
    private Instances getInstanceInicializadaColumnas(){
        Instances retorno = new Instances();
        for(String columna: this.Columns){
            retorno.addColumn(columna);
        }
        return retorno;
    }
    
    //filtrado
    /**
     * Retorna una Instances, con una lista de instancias dentro que siguen el patron de filtrado
     * los datos en la columna column deben ser iguales a data
     * @param column columna que se quiere comparar
     * @param data dato a comparar
     * @return Instances filtradas
     */
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
    /**
     * Retorna una Instances, con una lista de instancias dentro que siguen el patron de filtrado
     * los datos en la columna column deben no ser iguales a data
     * @param column columna que se quiere comparar
     * @param data dato a comparar
     * @return Instances filtradas
     */
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
    /**
     * retorna todos los ids distintos dado un nombre de columna
     * @param column nombre de columna
     * @return lista de identificadores distintos
     */
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
    /**
     * retorna solo las instancias que no sean ocultas
     * @return instancias fitradas, solo las no ocultas
     */
    public Instances getInstancesNoOcultas(){
        Instances retorno = this.getInstanceInicializadaColumnas();
        for(Instance inst : this.listaDeInstance){
            if(!inst.isOculto()){
                retorno.addInstance(inst);
            }
        }
         return retorno;
    }
    /**
     * retorna solo las instancias que sean ocultas
     * @return instancias fitradas, solo las ocultas
     */
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
