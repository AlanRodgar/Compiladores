/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compiladores.practicauno;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Alumno
 */
public class Estado {
    public static int idEdoActual = 0;
    private int idEstado, token;
    boolean estadoAceptacion;
    ArrayList<Transicion> transiciones;
    
    public Estado(){
        idEstado = idEdoActual++;
        estadoAceptacion = false;
        transiciones  = new ArrayList<>();
        transiciones.clear();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
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
        final Estado other = (Estado) obj;
        if (this.idEstado != other.idEstado) {
            return false;
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
    
    
    
    public void addTransicion(char s, Estado estadoDestino){
        Transicion t = new Transicion(s, estadoDestino);
        transiciones.add(t);
    }

    public void addTransicion(char simbMin, char simbMax, Estado estadoFinal) {
        Transicion t = new Transicion(simbMin, simbMax, estadoFinal);
        transiciones.add(t);
    }
    
    public boolean isIncluded(ArrayList<Estado> C){
        for(Estado j:C){
            if(j.equals(this))
                return true;
        }
        return false;
    }
    
    public boolean isIncluded(ArrayList<Estado> C, Estado e){
        for(Estado j:C){
            if(j.equals(e))
                return true;
        }
        return false;
    }
    
    /* Getters y Setters */

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public boolean isEstadoAceptacion() {
        return estadoAceptacion;
    }

    public void setEstadoAceptacion(boolean estadoAceptacion) {
        this.estadoAceptacion = estadoAceptacion;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<Transicion> transiciones) {
        this.transiciones = transiciones;
    }
    
}
