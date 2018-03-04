
package com.compiladores.practicauno;

import java.util.Objects;

/**
 *
 * @author Alumno
 */
public class Transicion {
    char minSimb;
    char maxSimb;
    Estado estadoDestino;
    
    public Transicion(char s, Estado estadoDestino){
    
        minSimb = s;
        maxSimb = s;
        this.estadoDestino = estadoDestino;
        
    }
    
    public Transicion(char minSimb, char maxSimb,  Estado estadoDestino){
    
        this.minSimb = minSimb;
        this.maxSimb = maxSimb;
        this.estadoDestino = estadoDestino;
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Transicion other = (Transicion) obj;
        if (this.minSimb != other.minSimb) {
            return false;
        }
        if (this.maxSimb != other.maxSimb) {
            return false;
        }
        if (!this.estadoDestino.equals(other.estadoDestino)) {
            return false;
        }
        return true;
    }
    
    
    
}
