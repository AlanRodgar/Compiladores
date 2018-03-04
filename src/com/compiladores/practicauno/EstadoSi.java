
package com.compiladores.practicauno;

import java.util.ArrayList;

/**
 *
 * @author Alan Rodgar
 */
public class EstadoSi {
    private static int idActual;

    public static int getIdActual() {
        return idActual;
    }

    public static void setIdActual(int aIdActual) {
        idActual = aIdActual;
    }
    private int idEstadoSi;
    private ArrayList<Estado> estados;
    private boolean analizado;
    private boolean estadoAceptacion;
    private int token;
    private ArrayList<TransicionSi> transiciones;

    public EstadoSi() {
        this(new ArrayList<Estado>());
    }

    public EstadoSi(ArrayList<Estado> estados) {
        idEstadoSi = idActual;
        idActual++;
        transiciones = new ArrayList<>();
        this.estados = estados;
        
        for(Estado estado: estados){
            if(estado.isEstadoAceptacion()){
                estadoAceptacion = true;
                token = estado.getToken();
                break;
            }
        }
        
    }

    public boolean isIncluded(ArrayList<EstadoSi> C){
        for(EstadoSi estado:C){
            if(estado.equals(this))
                return true;
        }
        return false;
    }
    
    public static boolean isIncluded(ArrayList<EstadoSi> C, EstadoSi estado){
        for(EstadoSi e:C){
            if(e.equals(estado))
                return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadoSi other = (EstadoSi) obj;
//        if (this.idEstadoSi != other.idEstadoSi) {
//            return false;
//        }
//        if (this.analizado != other.analizado) {
//            return false;
//        }
//        if (this.estadoAceptacion != other.estadoAceptacion) {
//            return false;
//        }
//        if (this.token != other.token) {
//            return false;
//        }
        
        /* Verificacion de lista de estados del AFN */
        if(other.estados.size() != this.estados.size()){
            return false;
        }
        else{
            for(int i=0; i<this.estados.size(); i++){
                if(!this.estados.get(i).equals(other.estados.get(i))){
                    return false;
                }
            }
        }

        /* Verificacion de transiciones */
        if(other.transiciones.size() != this.transiciones.size()){
            return false;
        }
        else{
            for(int i=0; i<this.transiciones.size(); i++){
                if(!this.transiciones.get(i).equals(other.transiciones.get(i))){
                    return false;
                }
            }
        }        
        
        return true;
    }
    
    /* Setters y Getters */

    public int getIdEstadoSi() {
        return idEstadoSi;
    }

    public void setIdEstadoSi(int idEstadoSi) {
        this.idEstadoSi = idEstadoSi;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public boolean isAnalizado() {
        return analizado;
    }

    public void setAnalizado(boolean analizado) {
        this.analizado = analizado;
    }

    public boolean isEstadoAceptacion() {
        return estadoAceptacion;
    }

    public void setEstadoAceptacion(boolean estadoAceptacion) {
        this.estadoAceptacion = estadoAceptacion;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public ArrayList<TransicionSi> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<TransicionSi> transiciones) {
        this.transiciones = transiciones;
    }
    
    
    
}

